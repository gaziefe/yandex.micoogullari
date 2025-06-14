class AuthRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val secureStorage: SecureStorage
) {
    suspend fun login(username: String, password: String) {
        val response = authApi.login(AuthRequest(username, password))
        secureStorage.saveTokens(response.accessToken, response.refreshToken)
    }

    suspend fun refreshToken() {
        val refreshToken = secureStorage.getRefreshToken() ?: throw IllegalStateException()
        val response = authApi.refreshToken(RefreshRequest(refreshToken))
        secureStorage.saveTokens(response.accessToken, response.refreshToken)
    }
}
