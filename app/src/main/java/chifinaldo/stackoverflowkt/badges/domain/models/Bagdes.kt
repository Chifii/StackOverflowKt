package chifinaldo.stackoverflowkt.badges.domain.models

import com.google.gson.annotations.SerializedName


data class BadgesList(
    @SerializedName("items")
    val items: List<Badge>,
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("quota_max")
    val quotaMax: Int,
    @SerializedName("quota_remaining")
    val quotaRemaining: Int
)

data class Badge(
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