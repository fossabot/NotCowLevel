package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.ContentModel
import com.google.gson.annotations.SerializedName

data class QuestionModel(
        @field:SerializedName("top_answer_vote_count")
        val topAnswerVoteCount: Int? = null,

        @field:SerializedName("lock_status")
        val lockStatus: Int? = null,

        @field:SerializedName("is_follow")
        val isFollow: Int? = null,

        @field:SerializedName("create_time")
        val createTime: Int? = null,

        @field:SerializedName("has_collect")
        val hasCollect: Int? = null,

        @field:SerializedName("neat_content")
        val neatContent: ContentModel? = null,

        @field:SerializedName("game_tags")
        val gameTags: List<Int?>? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("answer_count")
        val answerCount: Int? = null,

        @field:SerializedName("parent_tags")
        val parentTags: List<Int?>? = null,

        @field:SerializedName("follower_count")
        val followerCount: Int? = null,

        @field:SerializedName("content")
        val content: String? = null,

        @field:SerializedName("tags")
        val tags: List<Int?>? = null,

        @field:SerializedName("elite")
        val elite: Int? = null,

        @field:SerializedName("update_time")
        val updateTime: Int? = null,

        @field:SerializedName("user_id")
        val userId: Int? = null,

        @field:SerializedName("games")
        val games: List<Int?>? = null,

        @field:SerializedName("my_answer")
        val myAnswer: List<Any?>? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("status")
        val status: Int? = null
) : BaseModel()