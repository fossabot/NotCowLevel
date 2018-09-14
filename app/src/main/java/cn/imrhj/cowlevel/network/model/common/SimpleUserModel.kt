package cn.imrhj.cowlevel.network.model.common

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class SimpleUserModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("avatar") val avatar: String?,
        @SerializedName("intro") val intro: String?,
        @SerializedName("url_slug") val urlSlug: String?,
        @SerializedName("is_developer") val isDeveloper: Int?
) : BaseModel()
