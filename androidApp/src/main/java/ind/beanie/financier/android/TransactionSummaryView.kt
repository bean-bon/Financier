package ind.beanie.financier.android

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import ind.beanie.financier.model.Transaction

@Composable
fun TransactionSummaryView(
    transaction: Transaction,
    onClose: () -> Unit,
    onDelete: () -> Unit
) {
    Column {
        Text(transaction.name, fontWeight = FontWeight.ExtraBold)

    }
}