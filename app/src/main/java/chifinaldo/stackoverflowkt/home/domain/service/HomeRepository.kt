package chifinaldo.stackoverflowkt.home.domain.service

import chifinaldo.stackoverflowkt.base.domain.service.BaseRepository
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.home.domain.models.BadgesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HomeRepository : BaseRepository<HomeService>(HomeService::class.java) {
    suspend fun getBadges(): Result<BadgesList> {
        return withContext(Dispatchers.IO) {
            try {
                val data = service.getBadges()
                Result.Success(data)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }
}