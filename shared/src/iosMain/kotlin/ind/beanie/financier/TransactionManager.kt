package ind.beanie.financier

import ind.beanie.financier.db.DatabaseProvider
import ind.beanie.financier.model.Transaction
import ind.beanie.financier.viewmodel.TransactionList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Suppress("unused")
class TransactionManager: TransactionList {

    private val realm = DatabaseProvider.shared
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    private val scope = CoroutineScope(Dispatchers.IO)

    val transactions: StateFlow<List<Transaction>> = _transactions

    init {
        fetchData()
    }

    private fun fetchData() = scope.launch {
        val transactionList = realm.transactionDao.getTransactions()
        transactionList.collect {
            withContext(Dispatchers.Main) {
                _transactions.value = it.list
            }
        }
    }

    override fun addTransaction(transaction: Transaction) {
        runBlocking {
            realm.transactionDao.saveTransaction(transaction)
        }
    }

    override fun removeTransaction(transaction: Transaction) {
        runBlocking {
            realm.transactionDao.deleteTransaction(transaction)
        }
    }

}