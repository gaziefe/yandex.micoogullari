@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val syncNotesUseCase: SyncNotesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _notesState = MutableStateFlow<NotesState>(NotesState.Loading)
    val notesState: StateFlow<NotesState> = _notesState.asStateFlow()

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch(dispatcher) {
            getNotesUseCase()
                .catch { e ->
                    _notesState.value = NotesState.Error(e.message ?: "Unknown error")
                }
                .collect { notes ->
                    _notesState.value = NotesState.Success(notes)
                }
        }
    }

    fun syncNotes() {
        viewModelScope.launch(dispatcher) {
            try {
                _notesState.value = NotesState.Loading
                syncNotesUseCase()
                loadNotes() // Refresh after sync
            } catch (e: Exception) {
                _notesState.value = NotesState.Error(e.message ?: "Sync failed")
            }
        }
    }
}

sealed class NotesState {
    object Loading : NotesState()
    data class Success(val notes: List<Note>) : NotesState()
    data class Error(val message: String) : NotesState()
}
