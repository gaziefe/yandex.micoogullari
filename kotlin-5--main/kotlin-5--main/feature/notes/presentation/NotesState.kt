data class NotesState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class NotesEvent {
    data class LoadNotes(val forceRefresh: Boolean = false) : NotesEvent()
    data class DeleteNote(val id: String) : NotesEvent()
    data class NavigateToEdit(val noteId: String?) : NotesEvent()
}

sealed class NotesEffect {
    data class NavigateToEdit(val noteId: String?) : NotesEffect()
    data class ShowError(val message: String) : NotesEffect()
}
