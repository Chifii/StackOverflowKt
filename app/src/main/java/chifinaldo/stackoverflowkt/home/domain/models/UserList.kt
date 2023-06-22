package chifinaldo.stackoverflowkt.home.domain.models

import com.google.gson.annotations.SerializedName

data class UserList(
    @SerializedName("items")
    val items: List<User>,
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("backoff")
    val backoff: Int,
    @SerializedName("quota_max")
    val quotaMax: Int,
    @SerializedName("quota_remaining")
    val quotaRemaining: Int
)