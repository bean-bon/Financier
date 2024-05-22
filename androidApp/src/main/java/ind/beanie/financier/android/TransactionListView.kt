package ind.beanie.financier.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ind.beanie.financier.DateTimeUtility
import ind.beanie.financier.model.Transaction

@Composable
fun TransactionListView(
    vm: TransactionManager = viewModel(),
    onAddTransaction: () -> Unit,
    onOpenTransaction: (Transaction) -> Unit
) {

    val transactions by vm.transactions.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize().padding(start = 5.dp, end = 5.dp)) {
        LazyColumn {
            item {
                Text(
                    "Transactions",
                    Modifier.padding(10.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 48.sp
                )
                Divider()
            }
            items(transactions.sortedBy { it.dateTimeMillis }) {
                Column(Modifier.clickable { onOpenTransaction(it) }) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            it.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            DateTimeUtility.standardDateTimeFormat(it.dateTimeMillis),
                            textAlign = TextAlign.End
                        )
                    }
                    Row {
                        Text("$${it.amount}")
                    }
                }
                Divider()
            }
        }
        Button(onClick = onAddTransaction, Modifier.align(Alignment.BottomCenter)) {
            Text("+")
        }
    }

}