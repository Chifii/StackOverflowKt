package chifinaldo.stackoverflowkt.base.domain.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryFactory {

    private const val BASE_URL = "https://api.stackexchange.com/"
    private const val BASE_LOGIN_URL = "https://stackoverflow.com/oauth/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitForLogin(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_LOGIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}