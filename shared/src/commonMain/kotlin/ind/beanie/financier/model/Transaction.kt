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

    constructor(name: String, amount: Double, dateTimeMillis: Long, currencyCode: String) : this() {
        this.name = name
        this.amount = amount
        this.dateTimeMillis = dateTimeMillis
        this.currencyCode = currencyCode
    }

}
