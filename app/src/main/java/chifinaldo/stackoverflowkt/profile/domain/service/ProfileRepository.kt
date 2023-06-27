package chifinaldo.stackoverflowkt.profile.domain.service

import android.util.Log
import chifinaldo.stackoverflowkt.base.domain.service.BaseRepository
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.profile.domain.models.UserBadge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProfileRepository : BaseRepository<ProfileService>(ProfileService::class.java) {

    suspend fun getUserInfo(userId: String): Result<UserBadge> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("Profile Repository:", "userId: $userId")
                val data = service.getUserInfo(
                    userId = userId
                )
                Result.Success(data)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

}