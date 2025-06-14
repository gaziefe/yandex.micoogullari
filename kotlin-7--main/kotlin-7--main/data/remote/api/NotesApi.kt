interface NotesApi {
    @GET("notes")
    suspend fun getNotes(): List<NoteDto>

    @GET("notes/{id}")
    suspend fun getNote(@Path("id") noteId: String): NoteDto

    @POST("notes")
    suspend fun createNote(@Body note: NoteDto): NoteDto

    @PUT("notes/{id}")
    suspend fun updateNote(@Path("id") noteId: String, @Body note: NoteDto): NoteDto

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") noteId: String)
}
