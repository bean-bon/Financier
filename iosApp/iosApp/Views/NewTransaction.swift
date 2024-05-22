//
//  NewTransaction.swift
//  iosApp
//
//  Created by Benjamin Groom on 22/05/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NewTransaction: View {
    
    let onTransactionMade: (shared.Transaction) -> Void
    
    @State private var name: String = ""
    @State private var amount: Double = 0.0
    @State private var date: Date = Date()
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Transaction Name")
            TextField("Name", text: $name)
                .textFieldStyle(.roundedBorder)
            Text("Transaction Amount")
            TextField("Amount", value: $amount, format: .currency(code: "gbp"))
                .keyboardType(.decimalPad)
                .textFieldStyle(.roundedBorder)
            DatePicker("Transaction Date", selection: $date)
            Spacer()
            Button("Confirm") {
                if (!name.isEmpty && amount != 0) {
                    onTransactionMade(
                        Transaction(
                            name: name,
                            amount: amount,
                            dateTimeMillis: Int64(date.timeIntervalSince1970 * 1000),
                            currencyCode: "GBP"
                        )
                    )
                    dismiss()
                }
            }.frame(alignment: .center)
        }
        .padding()
        .navigationTitle("New Transaction")
        .navigationBarTitleDisplayMode(.large)
    }
}

#Preview {
    NavigationStack {
        NewTransaction { _ in }
    }
}
