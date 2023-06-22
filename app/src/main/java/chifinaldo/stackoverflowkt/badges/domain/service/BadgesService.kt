package chifinaldo.stackoverflowkt.badges.domain.service

import chifinaldo.stackoverflowkt.badges.domain.models.BadgesList
import retrofit2.http.GET
import retrofit2.http.Query

interface BadgesService {
    @GET("2.3/badges")
    suspend fun getBadges(
        @Query("pagesize") pageSize: Int = 99,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("site") site: String = "stackoverflow",
    ): BadgesList
}