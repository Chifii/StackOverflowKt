package chifinaldo.stackoverflowkt.profile.domain.models

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("account_id")
    val accountId: Int,
    @SerializedName("reputation")
    val reputation: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("accept_rate")
    val acceptRate: Int,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("link")
    val link: String
)
