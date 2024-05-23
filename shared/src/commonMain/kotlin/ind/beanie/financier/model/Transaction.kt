package ind.beanie.financier.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Transaction(): RealmObject {

    @PrimaryKey var _id: ObjectId = ObjectId()
    var name: String = ""
    var amount: Double = 0.0
    var dateTimeMillis: Long = 0
    var currencyCode: String = "GBP"
    var category: String = TransactionCategory.General.name

    constructor(name: String, amount: Double, dateTimeMillis: Long, currencyCode: String, category: TransactionCategory) : this() {
        this.name = name
        this.amount = amount
        this.dateTimeMillis = dateTimeMillis
        this.currencyCode = currencyCode
        this.category = category.name
    }

    fun localisedCategory(): String =
        optionalCategoryOf(category)?.localisedName?.get()
        ?: category

    private fun optionalCategoryOf(name: String): TransactionCategory? =
        try { TransactionCategory.valueOf(name) }
        catch (e: IllegalArgumentException) { null }

}



