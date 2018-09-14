package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName


data class IndexedCollectionModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("user_id") val userId: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("pic") val pic: String?,
        @SerializedName("content") val content: String?,
        @SerializedName("private") val private: Int?,
        @SerializedName("status") val status: Int?,
        @SerializedName("rec_count") val recCount: Int?,
        @SerializedName("comment_count") val commentCount: Int?,
        @SerializedName("pv") val pv: Int?,
        @SerializedName("uv") val uv: Int?,
        @SerializedName("follower_count") val followerCount: Int?,
        @SerializedName("content_total_count") val contentTotalCount: Int?,
        @SerializedName("elite") val elite: Int?,
        @SerializedName("elite_time") val eliteTime: Int?,
        @SerializedName("lock_comment") val lockComment: Int?,
        @SerializedName("hide_comment") val hideComment: Int?,
        @SerializedName("pics") val pics: String?,
        @SerializedName("type") val type: String?,
        @SerializedName("create_time") val createTime: Int?,
        @SerializedName("update_time") val updateTime: Int?
) : BaseModel()