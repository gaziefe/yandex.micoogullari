interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun saveNote(note: Note)
    suspend fun deleteNote(noteId: String)
}
