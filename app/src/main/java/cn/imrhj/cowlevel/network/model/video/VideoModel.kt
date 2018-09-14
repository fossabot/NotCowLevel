package cn.imrhj.cowlevel.network.model.video

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.SimpleUserModel
import com.google.gson.annotations.SerializedName


data class VideoModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("content") val content: String?,
        @SerializedName("pic") val pic: String?,
        @SerializedName("url") val url: String?,
        @SerializedName("origin_url") val originUrl: String?,
        @SerializedName("type") val type: Int?,
        @SerializedName("post_id") val postId: Int?,
        @SerializedName("is_origin") val isOrigin: Int?,
        @SerializedName("status") val status: Int?,
        @SerializedName("is_home") val isHome: Int?,
        @SerializedName("rank") val rank: Int?,
        @SerializedName("feed_to_follower") val feedToFollower: Int?,
        @SerializedName("comment_count") val commentCount: Int?,
        @SerializedName("pv") val pv: Int?,
        @SerializedName("uv") val uv: Int?,
        @SerializedName("vote_count") val voteCount: Int?,
        @SerializedName("elite") val elite: Int?,
        @SerializedName("elite_time") val eliteTime: Int?,
        @SerializedName("create_time") val createTime: Int?,
        @SerializedName("update_time") val updateTime: Int?,
        @SerializedName("feature_time") val featureTime: Int?,
        @SerializedName("user") val user: SimpleUserModel?,
        @SerializedName("publish_time_human") val publishTimeHuman: String?,
        @SerializedName("update_time_human") val updateTimeHuman: String?,
        @SerializedName("publish_time_before") val publishTimeBefore: String?
) : BaseModel()