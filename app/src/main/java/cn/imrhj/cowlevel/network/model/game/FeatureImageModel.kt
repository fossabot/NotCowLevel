package cn.imrhj.cowlevel.network.model.game

import com.google.gson.annotations.SerializedName

data class FeatureImageModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("user_id") val userId: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("content") val content: String?,
        @SerializedName("pic") val pic: String?,
        @SerializedName("type") val type: Int?,
        @SerializedName("show_type") val showType: Int?,
        @SerializedName("post_id") val postId: Int?,
        @SerializedName("is_origin") val isOrigin: Int?,
        @SerializedName("vote_count") val voteCount: Int?,
        @SerializedName("status") val status: Int?,
        @SerializedName("is_home") val isHome: Int?,
        @SerializedName("rank") val rank: Int?,
        @SerializedName("feed_to_follower") val feedToFollower: Int?,
        @SerializedName("comment_count") val commentCount: Int?,
        @SerializedName("pv") val pv: Int?,
        @SerializedName("uv") val uv: Int?,
        @SerializedName("create_time") val createTime: Int?,
        @SerializedName("update_time") val updateTime: Int?,
        @SerializedName("feature_time") val featureTime: Int?
)