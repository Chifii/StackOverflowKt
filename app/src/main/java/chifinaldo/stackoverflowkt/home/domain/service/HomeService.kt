package chifinaldo.stackoverflowkt.home.domain.service

import chifinaldo.stackoverflowkt.home.domain.models.BadgesList
import retrofit2.http.GET

interface HomeService {
    @GET("2.3/badges?page=1&pagesize=99&fromdate=1672531200&todate=1686441600&order=asc&sort=name&site=stackoverflow")
    suspend fun getBadges(): BadgesList
}