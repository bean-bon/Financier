//
//  TransactionSummary.swift
//  iosApp
//
//  Created by Benjamin Groom on 22/05/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TransactionSummary: View {
    
    let transaction: shared.Transaction
    
    var body: some View {
        VStack {
            Text("\(transaction.currencyCode) \(String(format: "%.2f", transaction.amount))")
            Text("Purchased on \(DateTimeUtility.shared.standardDateTimeFormat(epochMillis: transaction.dateTimeMillis))")
        }.navigationTitle(transaction.name)
    }
}

#Preview {
    NavigationStack {
        TransactionSummary(transaction: shared.Transaction(
            name: "Beans",
            amount: 23.45,
            dateTimeMillis: Int64(Date().timeIntervalSince1970 * 1000),
            currencyCode: "GBP")
        )
    }
}
