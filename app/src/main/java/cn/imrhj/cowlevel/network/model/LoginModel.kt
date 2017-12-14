package cn.imrhj.cowlevel.network.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
        @field:SerializedName("redirect_uri") val redirectUri: String? = null,
        @field:SerializedName("auth_token") val authToken: String? = null
) : BaseModel()