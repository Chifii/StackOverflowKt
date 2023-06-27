package chifinaldo.stackoverflowkt.profile.domain.models

import com.google.gson.annotations.SerializedName

data class UserBadgeInfo(
    @SerializedName("user")
    val user: UserInfo,
    @SerializedName("badge_type")
    val badgeType: String,
    @SerializedName("award_count")
    val awardCount: Int,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("badge_id")
    val badgeId: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String
)
