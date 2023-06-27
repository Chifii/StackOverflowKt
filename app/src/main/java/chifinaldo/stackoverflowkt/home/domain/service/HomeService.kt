package chifinaldo.stackoverflowkt.home.domain.service

import chifinaldo.stackoverflowkt.home.domain.models.UserList
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("2.3/users")
    suspend fun searchUser(
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "name",
        @Query("inname") inName: String,
        @Query("site") site: String = "stackoverflow",
    ): UserList
}