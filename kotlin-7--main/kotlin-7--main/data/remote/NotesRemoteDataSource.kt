class NotesRemoteDataSource @Inject constructor(
    private val notesApi: NotesApi,
    private val authDataSource: AuthRemoteDataSource
) {
    private val authInterceptor = AuthInterceptor(authDataSource)
    
    suspend fun getNotes(): List<NoteDto> {
        return withRetry {
            notesApi.getNotes()
        }
    }

    suspend fun createNote(note: NoteDto): NoteDto {
        return withRetry {
            notesApi.createNote(note)
        }
    }

    private suspend fun <T> withRetry(
        maxRetries: Int = 3,
        initialDelay: Long = 1000,
        maxDelay: Long = 10000,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(maxRetries - 1) { attempt ->
            try {
                return block()
            } catch (e: Exception) {
                if (e is HttpException && e.code() == 401) {
                    authDataSource.refreshToken()
                }
                if (attempt == maxRetries - 1) throw e
                delay(currentDelay)
                currentDelay = minOf(currentDelay * 2, maxDelay)
            }
        }
        return block() // Last attempt
    }
}
