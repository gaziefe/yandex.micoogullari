class NotesLocalDataSource @Inject constructor(
    private val noteDao: NoteDao
) : NotesRepository {
    override fun getNotes(forceRefresh: Boolean): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun saveNote(note: Note) {
        noteDao.insertOrUpdateNote(note)
    }

    override suspend fun deleteNote(noteId: String) {
        noteDao.deleteNote(noteId)
    }
}
