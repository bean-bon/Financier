package ind.beanie.financier.viewmodel

import ind.beanie.financier.model.Transaction

interface TransactionList {

    fun addTransaction(transaction: Transaction)
    fun removeTransaction(transaction: Transaction)

}