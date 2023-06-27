package chifinaldo.stackoverflowkt.profile.domain.service

import chifinaldo.stackoverflowkt.home.domain.models.UserList
import chifinaldo.stackoverflowkt.profile.domain.models.UserBadge
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {
    @GET("2.3/users/{userId}/badges")
    suspend fun getUserInfo(
        @Path("userId") userId: String,
        @Query("pagesize") pageSize: String = "100",
        @Query("order") order: String = "asc",
        @Query("sort") sort: String = "rank",
        @Query("site") site: String = "stackoverflow",
    ): UserBadge

    @GET("2.3/users/{userId}/badges")
    suspend fun getBadgeByRank(
        @Path("userId") userId: String,
        @Query("pagesize") pageSize: String = "100",
        @Query("order") order: String = "asc",
        @Query("max") max: String,
        @Query("min") min: String,
        @Query("sort") sort: String = "rank",
        @Query("site") site: String = "stackoverflow",
    ): UserBadge
}