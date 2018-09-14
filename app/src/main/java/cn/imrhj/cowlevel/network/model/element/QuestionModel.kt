package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.CommonIDModel
import cn.imrhj.cowlevel.network.model.common.ContentModel
import com.google.gson.annotations.SerializedName

data class QuestionModel(
        @SerializedName("top_answer_vote_count") val topAnswerVoteCount: Int? = null,
        @SerializedName("lock_status") val lockStatus: Int? = null,
        @SerializedName("is_follow") val isFollow: Int? = null,
        @SerializedName("create_time") val createTime: Int? = null,
        @SerializedName("has_collect") val hasCollect: Int? = null,
        @SerializedName("neat_content") val neatContent: ContentModel? = null,
        @SerializedName("game_tags") val gameTags: List<Int?>? = null,
        @SerializedName("title") val title: String? = null,
        @SerializedName("answer_count") val answerCount: Int? = null,
        @SerializedName("parent_tags") val parentTags: List<Int?>? = null,
        @SerializedName("follower_count") val followerCount: Int? = null,
        @SerializedName("content") val content: String? = null,
        @SerializedName("tags") val tags: List<Int?>? = null,
        @SerializedName("elite") val elite: Int? = null,
        @SerializedName("update_time") val updateTime: Int? = null,
        @SerializedName("user_id") val userId: Int? = null,
        @SerializedName("games") val games: List<Int?>? = null,
        @SerializedName("my_answer") val myAnswer: CommonIDModel? = null,
        @SerializedName("id") val id: Int? = null,
        @SerializedName("status") val status: Int? = null
) : BaseModel()