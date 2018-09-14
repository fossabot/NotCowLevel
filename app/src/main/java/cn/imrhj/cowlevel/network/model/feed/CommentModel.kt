package cn.imrhj.cowlevel.network.model.feed

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.ContentModel
import cn.imrhj.cowlevel.network.model.common.SimpleUserModel
import com.google.gson.annotations.SerializedName

data class CommentModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("pi_id") val piId: Int?,
        @SerializedName("create_time") val createTime: Int?,
        @SerializedName("update_time") val updateTime: Int?,
        @SerializedName("title") val title: String?,
        @SerializedName("content") val content: String?,
        @SerializedName("comment_count") val commentCount: Int?,
        @SerializedName("vote_count") val voteCount: Int?,
        @SerializedName("star") val star: Int?,
        @SerializedName("star_text") val starText: String?,
        @SerializedName("play_time") val playTime: Int?,
        @SerializedName("play_time_detail") val playTimeDetail: String?,
        @SerializedName("play_time_text") val playTimeText: String?,
        @SerializedName("brief_content") val briefContent: ContentModel?,
        @SerializedName("neat_content") val neatContent: ContentModel?,
        @SerializedName("voters") val voters: List<VotersModel>?,
        @SerializedName("voter_count") val voterCount: Int?,
        @SerializedName("can_edit") val canEdit: Int?,
        @SerializedName("is_wildrose") val isWildrose: Int?,
        @SerializedName("publish_time_human") val publishTimeHuman: String?,
        @SerializedName("update_time_human") val updateTimeHuman: String?,
        @SerializedName("publish_time_before") val publishTimeBefore: String?,
        @SerializedName("user") val user: SimpleUserModel?,
        @SerializedName("has_vote") val hasVote: Int?,
        @SerializedName("has_collect") val hasCollect: Int?
) : BaseModel()
