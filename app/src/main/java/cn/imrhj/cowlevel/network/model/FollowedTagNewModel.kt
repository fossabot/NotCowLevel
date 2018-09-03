package cn.imrhj.cowlevel.network.model

import com.google.gson.annotations.SerializedName

data class FollowedTagNewModel(
        @field:SerializedName("new_count")
        val newCount: Int? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("new_content")
        val newContent: NewContent? = null
) : BaseModel()