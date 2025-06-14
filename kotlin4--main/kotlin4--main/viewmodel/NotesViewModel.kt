import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()
    
    fun loadNotes() {
        viewModelScope.launch {
            // تحميل الملاحظات من المصدر (مثل قاعدة بيانات أو ملف)
            _notes.value = listOf(
                Note(
                    uid = "1",
                    title = "ملاحظة 1",
                    content = "محتوى الملاحظة الأولى",
                    color = 0xFFFF0000,
                    importance = Importance.HIGH
                ),
                Note(
                    uid = "2",
                    title = "ملاحظة 2",
                    content = "محتوى الملاحظة الثانية",
                    color = 0xFF00FF00,
                    importance = Importance.NORMAL
                )
            )
        }
    }
    
    fun deleteNote(uid: String) {
        viewModelScope.launch {
            _notes.value = _notes.value.filter { it.uid != uid }
        }
    }
}
