import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DateSelector(
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = selectedDate?.toString() ?: "لم يتم اختيار تاريخ",
            modifier = Modifier.weight(1f)
        )
        Button(onClick = { showDatePicker = true }) {
            Text("اختر تاريخ")
        }
    }
    
    if (showDatePicker) {
        val calendar = Calendar.getInstance()
        selectedDate?.let { calendar.time = it }
        
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateSelected(calendar.time)
                        showDatePicker = false
                    }
                ) { Text("موافق") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) { Text("إلغاء") }
            }
        ) {
            DatePicker(
                state = rememberDatePickerState(
                    initialSelectedDateMillis = calendar.timeInMillis
                ),
                onDateSelected = { millis ->
                    calendar.timeInMillis = millis
                }
            )
        }
    }
}
