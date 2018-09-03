package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class ElementChildItemModel(
        @field:SerializedName("question_count")
        val questionCount: Int? = null,

        @field:SerializedName("article_count")
        val articleCount: Int? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("post_count")
        val postCount: Int? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("follower_count")
        val followerCount: Int? = null,

        @field:SerializedName("content")
        val content: String? = null
) : BaseModel()