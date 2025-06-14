@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNotesRepository(localDataSource: NotesLocalDataSource): NotesRepository {
        return localDataSource
    }

    @Provides
    @Singleton
    fun provideGetNotesUseCase(repository: NotesRepository): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveNoteUseCase(repository: NotesRepository): SaveNoteUseCase {
        return SaveNoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(repository: NotesRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }
}
