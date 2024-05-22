package ind.beanie.financier.db

import ind.beanie.financier.model.Transaction
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface TransactionOperations {
    suspend fun saveTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    fun getTransactions(): Flow<ResultsChange<Transaction>>
}

class TransactionDao(private val realm: Realm): TransactionOperations {

    override suspend fun saveTransaction(transaction: Transaction) {
        realm.write {
            copyToRealm(transaction)
        }
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        realm.writeBlocking {
            findLatest(transaction)?.let {
                delete(it)
            }
        }
    }

    override fun getTransactions(): Flow<ResultsChange<Transaction>> {
        return realm.query<Transaction>().find().asFlow()
    }

}