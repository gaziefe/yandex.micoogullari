import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Date

data class NoteState(
    var title: String by mutableStateOf(""),
    var content: String by mutableStateOf(""),
    var color: Int by mutableStateOf(0xFFFFFFFF),
    var importance: Importance by mutableStateOf(Importance.NORMAL),
    var hasSelfDestruct: Boolean by mutableStateOf(false),
    var selfDestructDate: Date? by mutableStateOf(null)
)

enum class Importance {
    LOW, NORMAL, HIGH
}
