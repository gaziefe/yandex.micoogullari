class AuthInterceptor(
    private val authDataSource: AuthRemoteDataSource
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${authDataSource.getAccessToken()}")
            .build()
        
        var response = chain.proceed(request)
        
        if (response.code == 401) {
            authDataSource.refreshToken()
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${authDataSource.getAccessToken()}")
                .build()
            response = chain.proceed(newRequest)
        }
        
        return response
    }
}
