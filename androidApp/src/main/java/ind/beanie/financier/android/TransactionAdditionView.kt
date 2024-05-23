package ind.beanie.financier.android

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ind.beanie.financier.DateTimeUtility
import ind.beanie.financier.TextKeys
import ind.beanie.financier.model.Transaction
import ind.beanie.financier.model.TransactionCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionAdditionView(
    onAddTransaction: (Transaction) -> Unit
) {

    var transactionName by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    var transactionAmount by remember { mutableDoubleStateOf(0.00) }
    var amountError by remember { mutableStateOf<String?>(null) }

    var dateDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = DateTimeUtility.currentLocalDateMillis(),
        initialDisplayMode = DisplayMode.Picker
    )

    val timePickerState = rememberTimePickerState(
        initialHour = DateTimeUtility.currentTime().hour,
        initialMinute = DateTimeUtility.currentTime().minute,
        is24Hour = true
    )

    var dropdownMenuOpen by remember { mutableStateOf(false) }
    var categoryPickerState by remember { mutableStateOf(TransactionCategory.General) }

    val focusManager = LocalFocusManager.current

    val submitEnabled by remember { derivedStateOf {
        transactionName != "" && transactionAmount != 0.0
    } }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            TextKeys.Title.newTransaction.get(),
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 48.sp,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            value = transactionName,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                transactionName = it
                nameError =
                    if (transactionName.isEmpty()) TextKeys.Error.empty.get()
                    else null
            },
            label = { Text(TextKeys.Label.name.get()) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            isError = nameError != null,
            supportingText = { Text(nameError ?: "", color = Color.Red) }
        )
        OutlinedTextField(
            value = transactionAmount.toString(),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                transactionAmount = it.toDoubleOrNull() ?: transactionAmount
                amountError =
                    if (transactionAmount <= 0.0) TextKeys.Error.nonPositiveAmount.get()
                    else null
                },
            label = { Text(TextKeys.Label.amount.get()) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            supportingText = { Text(amountError ?: "", color = Color.Red) }
        )
        OutlinedTextField(
            DateTimeUtility.standardDateTimeFormat(
                datePickerState.selectedDateMillis ?: 0,
                timePickerState.millis()
            ),
            onValueChange = {},
            Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    dateDialog = it.isFocused
                },
            readOnly = true,
            label = { Text(TextKeys.Label.dateTime.get()) },
            supportingText = {}
        )
        if (dateDialog) {
            DateTimePickerDialog(
                datePickerState = datePickerState,
                timePickerState = timePickerState,
                onDismissRequest = {
                    dateDialog = false
                    focusManager.clearFocus()
                },
                confirmButton = {
                    Button(
                        {
                            dateDialog = false
                            focusManager.clearFocus()
                        }
                    ) {
                        Text(TextKeys.Button.confirm.get())
                    }
                }
            )
        }
        Column {
            OutlinedTextField(
                value = categoryPickerState.localisedName.get(),
                onValueChange = {},
                Modifier.onFocusChanged { if (it.isFocused) dropdownMenuOpen = true }.fillMaxWidth(),
                readOnly = true,
                label = { Text(TextKeys.Label.category.get()) },
                trailingIcon = {
                    AnimatedContent(
                        targetState = dropdownMenuOpen,
                        label = "Dropdown Arrow"
                    ) {
                        Icon(
                            if (it) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            TextKeys.Icon.dropdownArrow.get(),
                        )
                    }
                },
                supportingText = {}
            )
            DropdownMenu(
                expanded = dropdownMenuOpen,
                modifier = Modifier.fillMaxWidth(0.8f),
                onDismissRequest = {
                    dropdownMenuOpen = false
                    focusManager.clearFocus()
                }
            ) {
                for (category in TransactionCategory.entries) {
                    DropdownMenuItem(
                        text = { Text(category.localisedName.get()) },
                        onClick = {
                            categoryPickerState = category
                            dropdownMenuOpen = false
                            focusManager.clearFocus()
                        }
                    )
                }
            }
        }
        Button(
            {
                onAddTransaction(
                    Transaction(
                        transactionName,
                        transactionAmount,
                        datePickerState.selectedDateMillis?.let {
                            it + timePickerState.millis()
                        } ?: DateTimeUtility.currentLocalDateMillis(),
                        "GBP",
                        categoryPickerState
                    )
                )
            },
            enabled = submitEnabled
        ) {
            Text(TextKeys.Button.addTransaction.get())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.millis(): Long {
    return this.hour * 3_600_000L + this.minute * 60_000L
}