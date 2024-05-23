package ind.beanie.financier.model

import ind.beanie.financier.LocalisedText

enum class TransactionCategory(val localisedName: LocalisedText) {
    General(
        LocalisedText(
            "general",
            "en" to "General",
            "fr" to "Général"
        )
    ),
    Entertainment(
        LocalisedText(
            "entertainment",
            "en" to "Entertainment",
            "fr" to "Divertissement"
        )
    ),
    Expenses(
        LocalisedText(
            "expenses",
            "en" to "Expenses",
            "fr" to "Dépenses"
        )
    ),
    Family(
        LocalisedText(
            "family",
            "en" to "Family",
            "fr" to "Famille"
        )
    ),
    Food(
        LocalisedText(
            "food",
            "en" to "Food",
            "fr" to "Alimentaire"
        )
    ),
    Savings(
        LocalisedText(
            "savings",
            "en" to "Savings",
            "fr" to "Épargne"
        )
    ),
    Transfers(
        LocalisedText(
            "transfers",
            "en" to "Transfers",
            "fr" to "Virement"
        )
    ),
    Transport(
        LocalisedText(
            "transport",
            "en" to "Transport",
            "fr" to "Transport"
        )
    )
}