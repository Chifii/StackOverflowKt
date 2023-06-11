package chifinaldo.stackoverflowkt.login.domain.service

import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.base.domain.service.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LoginRepository : BaseRepository<LoginService>(LoginService::class.java) {
    suspend fun launchLogin(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val data = serviceForLogin.launchLogin(
                    "26552",
                    "private_info",
                    "dev-ok8urwnbpgn43nfk.us.auth0.com",
                    null
                )
                Result.Success(data)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }
}