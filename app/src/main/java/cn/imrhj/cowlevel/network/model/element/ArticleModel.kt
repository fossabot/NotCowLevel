package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.SimpleUserModel
import com.google.gson.annotations.SerializedName

data class ArticleModel(
        @SerializedName("comment_count") val commentCount: Int? = null,
        @SerializedName("create_time") val createTime: Int? = null,
        @SerializedName("has_collect") val hasCollect: Int? = null,
        @SerializedName("has_vote") val hasVote: Int? = null,
        @SerializedName("pic") val pic: String? = null,
        @SerializedName("title") val title: String? = null,
        @SerializedName("content") val content: String? = null,
        @SerializedName("update_time_human") val updateTimeHuman: String? = null,
        @SerializedName("update_time") val updateTime: Int? = null,
        @SerializedName("publish_time_before") val publishTimeBefore: String? = null,
        @SerializedName("publish_time") val publishTime: Int? = null,
        @SerializedName("id") val id: Int? = null,
        @SerializedName("vote_count") val voteCount: Int? = null,
        @SerializedName("user") val user: SimpleUserModel? = null,
        @SerializedName("publish_time_human") val publishTimeHuman: String? = null
) : BaseModel()