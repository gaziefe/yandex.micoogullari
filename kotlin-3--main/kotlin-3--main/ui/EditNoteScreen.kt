import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    state: NoteState,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // حقل العنوان
        OutlinedTextField(
            value = state.title,
            onValueChange = { state.title = it },
            label = { Text("Title of the note") },
            modifier = Modifier.fillMaxWidth()
        )

        // حقل المحتوى (يتوسع تلقائياً)
        var textFieldHeight by remember { mutableStateOf(100.dp) }
        OutlinedTextField(
            value = state.content,
            onValueChange = { state.content = it },
            label = { Text("Content of the note") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 500.dp),
            maxLines = Int.MAX_VALUE
        )

        // مفتاح تفعيل التاريخ
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = state.hasSelfDestruct,
                onCheckedChange = { state.hasSelfDestruct = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Activation completion date")
        }

        // عرض محدد التاريخ إذا كان مفعل
        if (state.hasSelfDestruct) {
            DateSelector(
                selectedDate = state.selfDestructDate,
                onDateSelected = { state.selfDestructDate = it }
            )
        }

        // محدد الألوان
        ColorPicker(
            selectedColor = state.color,
            onColorSelected = { state.color = it }
        )

        // محدد الأهمية
        ImportanceSelector(
            selectedImportance = state.importance,
            onImportanceSelected = { state.importance = it }
        )

        // زر الحفظ
        Button(
            onClick = onSave,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}

@Composable
private fun ImportanceSelector(
    selectedImportance: Importance,
    onImportanceSelected: (Importance) -> Unit
) {
    Column {
        Text("Level of importance", style = MaterialTheme.typography.labelMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Importance.values().forEach { importance ->
                FilterChip(
                    selected = importance == selectedImportance,
                    onClick = { onImportanceSelected(importance) },
                    label = { Text(importance.name) }
                )
            }
        }
    }
}
