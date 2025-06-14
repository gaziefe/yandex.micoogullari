class GetNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(): Flow<List<Note>> = repository.getNotes()
}

class SyncNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke() {
        // Implementation for full sync
    }
}
