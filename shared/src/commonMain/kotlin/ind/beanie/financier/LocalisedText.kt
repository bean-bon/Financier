package ind.beanie.financier

expect class LocalisedText(
    id: String,
    vararg localisedText: Pair<String, String>
) {
    fun get(): String
    fun get(locale: String): String
}

object TextKeys {
    object Title {
        val newTransaction = LocalisedText(
            "new_transaction",
            "en" to "New transaction",
            "fr" to "Nouvelle transaction"
        )
        val transactions = LocalisedText(
            "transactions",
            "en" to "Transactions",
            "fr" to "Transactions"
        )
    }
    object Button {
        val addTransaction = LocalisedText(
            "add_transaction",
            "en" to "Add transaction",
            "fr" to "Ajouter une transaction"
        )
        val confirm = LocalisedText(
            "confirm",
            "en" to "Confirm",
            "fr" to "Confirmer"
        )
    }
    object Label {
        val name = LocalisedText(
            "name",
            "en" to "Name",
            "fr" to "Nom"
        )
        val amount = LocalisedText(
            "amount",
            "en" to "Amount",
            "fr" to "Montant"
        )
        val date = LocalisedText(
            "date",
            "en" to "Date",
            "fr" to "Date"
        )
        val dateTime = LocalisedText(
            "date_time",
            "en" to "Date and time",
            "fr" to "Date et heure"
        )
        val category = LocalisedText(
            "category",
            "en" to "Category",
            "fr" to "Catégorie"
        )
    }
    object Icon {
        val dropdownArrow = LocalisedText(
            "dropdown_arrow",
            "en" to "Dropdown arrow",
            "fr" to "Flèche déroulante"
        )
    }
    object Error {
        val empty = LocalisedText(
            "empty_error",
            "en" to "Cannot be empty.",
            "fr" to "Ne peut pas être vide."
        )
        val nonPositiveAmount = LocalisedText(
            "non_positive_amount",
            "en" to "Must be a value greater than 0.",
            "fr" to "Le montant doit être supérieur à 0."
        )
    }
    object Affirmation {
        val purchasedOn = LocalisedText(
            "purchased_on",
            "en" to "Purchased on",
            "fr" to "Acheté le"
        )
    }
}