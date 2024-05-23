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
    private let categories = TransactionCategory.allCases
    
    @State private var name: String = ""
    @State private var amount: Double = 0.0
    @State private var date: Date = Date()
    @State private var category: TransactionCategory = .general
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(TextKeys.Label.shared.name.get())
            TextField(TextKeys.Label.shared.name.get(), text: $name)
                .textFieldStyle(.roundedBorder)
            Text(TextKeys.Label.shared.amount.get())
            TextField(TextKeys.Label.shared.amount.get(), value: $amount, format: .currency(code: "gbp"))
                .keyboardType(.decimalPad)
                .textFieldStyle(.roundedBorder)
            DatePicker(TextKeys.Label.shared.dateTime.get(), selection: $date)
            HStack {
                Text(TextKeys.Label.shared.category.get())
                Spacer()
                Picker(
                    TextKeys.Label.shared.category.get(),
                    selection: $category
                ) {
                    ForEach(categories, id: \.self) { c in
                        Text(c.localisedName.get())
                    }
                }
            }
            Spacer()
            Button(TextKeys.Button.shared.confirm.get()) {
                if (!name.isEmpty && amount != 0) {
                    onTransactionMade(
                        Transaction(
                            name: name,
                            amount: amount,
                            dateTimeMillis: Int64(date.timeIntervalSince1970 * 1000),
                            currencyCode: "GBP",
                            category: category
                        )
                    )
                    dismiss()
                }
            }.frame(alignment: .center)
        }
        .padding()
        .navigationTitle(TextKeys.Title.shared.newTransaction.get())
        .navigationBarTitleDisplayMode(.large)
    }
}

extension NewTransaction {
    class Accessibility {
        private init() {}
        static let title = "NewTransactionTitle"
    }
}

#Preview {
    NavigationStack {
        NewTransaction { _ in }
    }
}
