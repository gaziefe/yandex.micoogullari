class NotesLocalDataSource @Inject constructor(
    private val noteDao: NoteDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun saveNote(note: Note) = withContext(dispatcher) {
        noteDao.insertOrUpdateNote(note)
    }

    suspend fun deleteNote(noteId: String) = withContext(dispatcher) {
        noteDao.deleteNote(noteId)
    }

    fun getNotes(): Flow<List<Note>> = noteDao.getAllNotes()
}
