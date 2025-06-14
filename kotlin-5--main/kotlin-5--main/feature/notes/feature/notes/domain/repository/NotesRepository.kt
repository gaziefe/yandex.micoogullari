interface NotesRepository {
    fun getNotes(forceRefresh: Boolean): Flow<List<Note>>
    suspend fun saveNote(note: Note)
    suspend fun deleteNote(noteId: String)
}
