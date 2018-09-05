package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.SimpleUserModel
import com.google.gson.annotations.SerializedName

data class ArticleModel(
        @field:SerializedName("comment_count")
        val commentCount: Int? = null,

        @field:SerializedName("create_time")
        val createTime: Int? = null,

        @field:SerializedName("has_collect")
        val hasCollect: Int? = null,

        @field:SerializedName("has_vote")
        val hasVote: Int? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("content")
        val content: String? = null,

        @field:SerializedName("update_time_human")
        val updateTimeHuman: String? = null,

        @field:SerializedName("update_time")
        val updateTime: Int? = null,

        @field:SerializedName("publish_time_before")
        val publishTimeBefore: String? = null,

        @field:SerializedName("publish_time")
        val publishTime: Int? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("vote_count")
        val voteCount: Int? = null,

        @field:SerializedName("user")
        val user: SimpleUserModel? = null,

        @field:SerializedName("publish_time_human")
        val publishTimeHuman: String? = null
) : BaseModel()