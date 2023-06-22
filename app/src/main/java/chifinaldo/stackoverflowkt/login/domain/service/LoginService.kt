package chifinaldo.stackoverflowkt.login.domain.service

import chifinaldo.stackoverflowkt.login.domain.models.AccessTokenResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("dialog")
    suspend fun launchLogin(
        @Query("client_id") clientId: String,
        @Query("scope") scope: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("state") state: String?
    ): String

    @POST("access_token/json")
    suspend fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Query("redirect_uri") redirectUri: String
    ): AccessTokenResponse
}