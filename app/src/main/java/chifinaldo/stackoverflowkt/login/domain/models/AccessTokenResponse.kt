package chifinaldo.stackoverflowkt.login.domain.models

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("error_message")
    val errorMessage: String?
)
