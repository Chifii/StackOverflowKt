package chifinaldo.stackoverflowkt.badges.domain.service

import android.util.Log
import chifinaldo.stackoverflowkt.base.domain.service.BaseRepository
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.badges.domain.models.BadgesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class BadgesRepository : BaseRepository<BadgesService>(BadgesService::class.java) {
    suspend fun getBadges(
        order: Boolean = true,
        sort: String? = null
    ): Result<BadgesList> {
        return withContext(Dispatchers.IO) {
            try {
                val data = service.getBadges(
                    order = if (order) "asc" else "desc",
                    sort = sort ?: "name"
                )
                Log.d(
                    "Servicio:",
                    "Querys Param: Order:${if (order) "asc" else "desc"}, Sort: $sort"
                )
                Log.d("Servicio:", "Quota: ${data.quotaRemaining}")
                Result.Success(data)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }
}