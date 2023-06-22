package chifinaldo.stackoverflowkt.login.domain.service

import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.base.domain.service.BaseRepository
import chifinaldo.stackoverflowkt.login.domain.models.AccessTokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Url

class LoginRepository : BaseRepository<LoginService>(LoginService::class.java) {
    suspend fun launchLogin(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val data = serviceForLogin.launchLogin(
                    "26611",
                    "no_expiry",
                    "https://chifinaldo.stackoverflowkt/stackoverkt",
                    null
                )
                Result.Success(data)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

    suspend fun getAccessToken(code: String): Result<AccessTokenResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val data = serviceForLogin.getAccessToken(
                    "26611",
                    "U7hMElYMUP5FtGDqz9lV1Q((",
                    code,
                    "https://chifinaldo.stackoverflowkt/stackoverkt",
                )
                Result.Success(data)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }
}