class NotesRemoteDataSource @Inject constructor(
    private val notesApi: NotesApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchNotes(): List<Note> = withContext(dispatcher) {
        notesApi.getNotes()
    }

    suspend fun syncNote(note: Note) = withContext(dispatcher) {
        notesApi.syncNote(note)
    }

    suspend fun deleteNote(noteId: String) = withContext(dispatcher) {
        notesApi.deleteNote(noteId)
    }
}
