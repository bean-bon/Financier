package ind.beanie.financier.db

import ind.beanie.financier.model.Transaction
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlin.concurrent.Volatile

/**
 * Singleton DAO for app data interaction.
 */
object DatabaseProvider {

    private val config = RealmConfiguration.create(
        schema = setOf(Transaction::class)
    )

    @Volatile private var _instance: LocalDatabase? = null

    /**
     * A shared database instance for the application.
     */
    val shared: LocalDatabase
        get() = _instance ?: createInstance().also { _instance = it }

    private fun createInstance(): LocalDatabase {
        return LocalDatabase(Realm.open(config))
    }

    /**
     * Change the instance of LocalDatabase used in the code.
     * This should only be used in test environments.
     * @param localDatabase A LocalDatabase instance to use for testing.
     */
    fun setTestInstance(localDatabase: LocalDatabase) {
        _instance = localDatabase
    }

}

class LocalDatabase(
    realm: Realm
) {
    val transactionDao: TransactionOperations = TransactionDao(realm)
}