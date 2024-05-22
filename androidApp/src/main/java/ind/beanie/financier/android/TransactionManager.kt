package ind.beanie.financier.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ind.beanie.financier.db.DatabaseProvider
import ind.beanie.financier.viewmodel.TransactionList
import ind.beanie.financier.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class TransactionManager: ViewModel(), TransactionList {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    init {
        observeData()
    }

    private fun observeData() = viewModelScope.launch {
        val transactionList = DatabaseProvider.shared.transactionDao.getTransactions()
        transactionList.collect {
            _transactions.value = it.list
        }
    }

    fun getTransaction(id: ObjectId): Transaction? = transactions.value.firstOrNull {
        it._id == id
    }

    override fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            DatabaseProvider.shared.transactionDao.saveTransaction(transaction)
        }
    }

    override fun removeTransaction(transaction: Transaction) {
        viewModelScope.launch {
            DatabaseProvider.shared.transactionDao.deleteTransaction(transaction)
        }
    }

}