import SwiftUI

import shared

struct TransactionList: View {
	
    @ObservedObject private(set) var viewModel: ViewModel

    var body: some View {
        ListView(transactions: viewModel.transactions) { t in
            viewModel.transactionManager.addTransaction(transaction: t)
        } onDeleteTransaction: { t in
            viewModel.transactionManager.removeTransaction(transaction: t)
        }
        .onAppear { self.viewModel.startObserving() }
	}
}

extension TransactionList {
    
    @MainActor
    class ViewModel: ObservableObject {
        
        @Published var transactions: [shared.Transaction] = []
        let transactionManager = TransactionManager()
        
        func startObserving() {
            Task {
                let transactions = self.transactionManager.transactions
                for await ts in transactions {
                    self.transactions = ts
                }
            }
        }
        
    }
}

struct ListView: View {
    
    let transactions: [shared.Transaction]
    let onAddTransaction: (shared.Transaction) -> Void
    let onDeleteTransaction: (shared.Transaction) -> Void
            
    var body: some View {
        NavigationStack {
            List {
                ForEach(transactions, id: \.self) { t in
                    NavigationLink {
                        TransactionSummary(transaction: t)
                    } label: {
                        VStack(alignment: .leading) {
                            HStack {
                                Text(t.name)
                                    .bold()
                                Spacer()
                                Text(DateTimeUtility.shared.standardDateTimeFormat(epochMillis: t.dateTimeMillis))
                            }
                            HStack {
                                Text(t.currencyCode)
                                Text(String(t.amount))
                            }
                        }
                    }
                }.onDelete(perform: { indexSet in
                    onDeleteTransaction(transactions[indexSet.count])
                })
            }.navigationTitle("Transactions")
            NavigationLink("Add transaction") {
                NewTransaction(onTransactionMade: onAddTransaction)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        TransactionList(viewModel: TransactionList.ViewModel())
	}
}
