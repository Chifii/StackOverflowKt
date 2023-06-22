package chifinaldo.stackoverflowkt.badges.domain.models

import com.google.gson.annotations.SerializedName

data class BadgeType(
    @SerializedName("bronze")
    val bronze: Int,
    @SerializedName("silver")
    val silver: Int,
    @SerializedName("gold")
    val gold: Int
)