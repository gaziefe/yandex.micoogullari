import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(
    selectedColor: Int,
    onColorSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        0xFFFF0000 to "Red",
        0xFFFFFF00 to "Yellow",
        0xFF00FF00 to "Green",
        0xFF0000FF to "Blue",
        0xFFFF00FF to "Magenta"
    )
    
    Column(modifier) {
        Text("اchosse a color", style = MaterialTheme.typography.labelMedium)
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            colors.forEach { (color, _) ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(color))
                        .border(
                            width = if (color == selectedColor) 2.dp else 1.dp,
                            color = if (color == selectedColor) Color.Black else Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable { onColorSelected(color) }
                ) {
                    if (color == selectedColor) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            
            var showCustomPicker by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { showCustomPicker = true }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { showCustomPicker = true }
                        )
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Palette,
                    contentDescription = "Custom Color",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            if (showCustomPicker) {
                CustomColorPicker(
                    initialColor = selectedColor,
                    onColorSelected = {
                        onColorSelected(it)
                        showCustomPicker = false
                    },
                    onDismiss = { showCustomPicker = false }
                )
            }
        }
    }
}

@Composable
private fun CustomColorPicker(
    initialColor: Int,
    onColorSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
}
