package chifinaldo.stackoverflowkt.base.domain.service

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryFactory {

    private const val BASE_URL = "https://api.stackexchange.com/"
    private const val BASE_LOGIN_URL = "https://stackoverflow.com/oauth/"

    private val loginInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = okhttp3.OkHttpClient.Builder().apply {
        addInterceptor(loginInterceptor)
    }.build()

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    fun getRetrofitForLogin(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_LOGIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}