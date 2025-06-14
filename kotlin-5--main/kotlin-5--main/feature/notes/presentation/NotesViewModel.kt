import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<NotesEffect>()
    val effect: SharedFlow<NotesEffect> = _effect.asSharedFlow()

    fun handleEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.LoadNotes -> loadNotes(event.forceRefresh)
            is NotesEvent.DeleteNote -> deleteNote(event.id)
            is NotesEvent.NavigateToEdit -> navigateToEdit(event.noteId)
        }
    }

    private fun loadNotes(forceRefresh: Boolean) {
        viewModelScope.launch {
            getNotesUseCase(forceRefresh)
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update { it.copy(error = e.message) }
                    _effect.emit(NotesEffect.ShowError(e.message ?: "Unknown error"))
                }
                .collect { notes ->
                    _state.update { it.copy(notes = notes, isLoading = false) }
                }
        }
    }

    private fun deleteNote(id: String) {
        viewModelScope.launch {
            deleteNoteUseCase(id)
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update { it.copy(error = e.message) }
                    _effect.emit(NotesEffect.ShowError(e.message ?: "Delete failed"))
                }
                .collect {
                    loadNotes(true)
                }
        }
    }

    private fun navigateToEdit(noteId: String?) {
        viewModelScope.launch {
            _effect.emit(NotesEffect.NavigateToEdit(noteId))
        }
    }
}
