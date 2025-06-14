interface SecureStorage {
    fun saveTokens(accessToken: String, refreshToken: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearTokens()
}

@Singleton
class SecureStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SecureStorage {
    private val sharedPrefs = context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)

    override fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPrefs.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .apply()
    }

    override fun getAccessToken(): String? {
        return sharedPrefs.getString("access_token", null)
    }

    override fun getRefreshToken(): String? {
        return sharedPrefs.getString("refresh_token", null)
    }

    override fun clearTokens() {
        sharedPrefs.edit()
            .remove("access_token")
            .remove("refresh_token")
            .apply()
    }
}
