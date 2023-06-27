package chifinaldo.stackoverflowkt.profile.domain.models

import com.google.gson.annotations.SerializedName


data class UserBadge(
    @SerializedName("items")
    val items: List<UserBadgeInfo>
)