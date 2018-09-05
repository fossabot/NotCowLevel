package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class ElementModel(

        @field:SerializedName("is_dislike")
        val isDislike: Int? = null,

        @field:SerializedName("pv")
        val pv: Int? = null,

        @field:SerializedName("to_id")
        val toId: Int? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("follower_count")
        val followerCount: Int? = null,

        @field:SerializedName("content")
        val content: String? = null,

        @field:SerializedName("new_content")
        val newContent: String? = null,

        @field:SerializedName("update_time_human")
        val updateTimeHuman: String? = null,

        @field:SerializedName("update_time")
        val updateTime: Int? = null,

        @field:SerializedName("new_time")
        val newTime: Int? = null,

        @field:SerializedName("article_count")
        val articleCount: Int = 0,

        @field:SerializedName("parent_count")
        val parentCount: Int = 0,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("lock_map")
        val lockMap: String? = null,

        @field:SerializedName("uv")
        val uv: Int? = null,

        @field:SerializedName("is_follow")
        val isFollow: Int? = null,

        @field:SerializedName("create_time")
        val createTime: Int? = null,

        @field:SerializedName("has_collect")
        val hasCollect: Int? = null,

        @field:SerializedName("child_count")
        val childCount: Int = 0,

        @field:SerializedName("can_edit")
        val canEdit: Int? = null,

        @field:SerializedName("question_count")
        val questionCount: Int = 0,

        @field:SerializedName("publish_time_before")
        val publishTimeBefore: String? = null,

        /**
         * 0 显示游戏标签, 1 隐藏游戏标签
         */
        @field:SerializedName("not_show_game")
        val notShowGame: Int = 1,

        @field:SerializedName("new_count")
        val newCount: Int = 0,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("video_count")
        val videoCount: Int = 0,

        @field:SerializedName("post_count")
        val postCount: Int = 0,

        @field:SerializedName("publish_time_human")
        val publishTimeHuman: String? = null,

        @field:SerializedName("status")
        val status: Int? = null
) : BaseModel()