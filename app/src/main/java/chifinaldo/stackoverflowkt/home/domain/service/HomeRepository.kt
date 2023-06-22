package chifinaldo.stackoverflowkt.home.domain.service

import chifinaldo.stackoverflowkt.base.domain.service.BaseRepository
import chifinaldo.stackoverflowkt.home.domain.models.UserList
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HomeRepository : BaseRepository<HomeService>(HomeService::class.java) {

    suspend fun searchUser(userName: String?): Result<UserList> {
        return withContext(Dispatchers.IO) {
            try {
                val data = service.searchUser(
                    inName = userName ?: ""
                )
                Result.Success(data)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

}