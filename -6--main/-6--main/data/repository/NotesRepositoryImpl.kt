class NotesRepositoryImpl @Inject constructor(
    private val localDataSource: NotesLocalDataSource,
    private val remoteDataSource: NotesRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NotesRepository {
    override fun getNotes(): Flow<List<Note>> = flow {
        // Emit local data first
        localDataSource.getNotes().collect { localNotes ->
            emit(localNotes)
        }

        // Then try to sync with remote
        try {
            val remoteNotes = remoteDataSource.fetchNotes()
            remoteNotes.forEach { note ->
                localDataSource.saveNote(note)
            }
        } catch (e: Exception) {
            // Handle error, but keep emitting local data
        }
    }

    override suspend fun saveNote(note: Note) = withContext(dispatcher) {
        localDataSource.saveNote(note)
        try {
            remoteDataSource.syncNote(note)
        } catch (e: Exception) {
            // Queue for later sync
        }
    }

    override suspend fun deleteNote(noteId: String) = withContext(dispatcher) {
        localDataSource.deleteNote(noteId)
        try {
            remoteDataSource.deleteNote(noteId)
        } catch (e: Exception) {
            // Queue for later sync
        }
    }
}
