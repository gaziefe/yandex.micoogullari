// GetNotesUseCase.kt
class GetNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(forceRefresh: Boolean): Flow<List<Note>> {
        return repository.getNotes(forceRefresh)
    }
}

// SaveNoteUseCase.kt
class SaveNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.saveNote(note)
    }
}

// DeleteNoteUseCase.kt
class DeleteNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: String) {
        repository.deleteNote(noteId)
    }
}
class GetNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(forceRefresh: Boolean): Flow<List<Note>> {
        return repository.getNotes(forceRefresh)
    }
}

// SaveNoteUseCase.kt
class SaveNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.saveNote(note)
    }
}

// DeleteNoteUseCase.kt
class DeleteNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: String) {
        repository.deleteNote(noteId)
    }
}
