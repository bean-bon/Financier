package ind.beanie.financier.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ind.beanie.financier.DateTimeUtility
import ind.beanie.financier.TextKeys
import ind.beanie.financier.model.Transaction

@Composable
fun TransactionListView(
    vm: TransactionManager = viewModel(),
    onAddTransaction: () -> Unit,
    onOpenTransaction: (Transaction) -> Unit
) {

    val transactions by vm.transactions.collectAsStateWithLifecycle()

    Box(
        Modifier
            .fillMaxSize()
            .padding(start = 5.dp, end = 5.dp)) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Text(
                    TextKeys.Title.transactions.get(),
                    Modifier.padding(10.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 48.sp
                )
                Divider()
            }
            items(transactions.sortedByDescending { it.dateTimeMillis }) {
                Column(Modifier.clickable { onOpenTransaction(it) }) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            it.name,
                            fontWeight = FontWeight.Bold
                        )
                        Text(DateTimeUtility.standardDateTimeFormat(it.dateTimeMillis))
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("$${it.amount}")
                        Spacer(Modifier)
                        Text(it.localisedCategory())
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