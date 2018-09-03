package cn.imrhj.cowlevel.network.model

import com.google.gson.annotations.SerializedName

data class FollowedPostNewModel(

        @field:SerializedName("url_slug")
        val urlSlug: String? = null,

        @field:SerializedName("new_count")
        val newCount: Int? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("new_content")
        val newContent: NewContent? = null
) : BaseModel()