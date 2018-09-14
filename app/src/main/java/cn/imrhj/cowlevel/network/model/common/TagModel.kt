package cn.imrhj.cowlevel.network.model.common

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class TagModel(
        @SerializedName("name") val name: String? = null,
        @SerializedName("id") val id: Int? = null,
        @SerializedName("pic") val pic: String? = null,
        @SerializedName("follower_count") val followerCount: Int? = null,
        @field:SerializedName("content") val content: String? = null
) : BaseModel()