package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.ContentModel
import cn.imrhj.cowlevel.network.model.common.SimpleUserModel
import com.google.gson.annotations.SerializedName


/**
 * 我的评价,用户评价
 *
 */
//{
//    "id":189967,
//    "play_status":2,
//    "star":5,
//    "play_time":0,
//    "play_time_detail":"120",
//    "feed_to_follower":1,
//    "play_time_text":"玩过 120 小时",
//    "star_text":"不可错过的神作",
//    "publish_time_human":"2018-09-15 11:16:50",
//    "update_time_human":"2018-09-15 11:20:04",
//    "publish_time_before":"3分钟前",
//    "review_id":3322097,
//    "title":"",
//    "content":"塞尔达天下第一!",
//    "editor_type":1,
//    "editor_content":"塞尔达天下第一!",
//    "comment_count":0,
//    "vote_count":0,
//    "neat_content":{
//    "desc":"塞尔达天下第一!",
//    "thumb":""
//},
//    "update_time_show":"2018年09月15日",
//    "share_option":2,
//    "has_vote":0,
//    "user":{
//    "name":"Hexer",
//    "avatar":"https://pic1.cdncl.net/user/hexer/common_pic/10eef354455fecc6ac785920f82950d2.jpg",
//    "intro":"Developer",
//    "url_slug":"hexer"
//},
//    "is_wildrose":0
//}
data class PostInterestModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("play_status") val playStatus: Int?,
        @SerializedName("star") val star: Int?,
        @SerializedName("play_time") val playTime: Int?,
        @SerializedName("play_time_detail") val playTimeDetail: String?,
        @SerializedName("feed_to_follower") val feedToFollower: Int?,
        @SerializedName("play_time_text") val playTimeText: String?,
        @SerializedName("star_text") val starText: String?,
        @SerializedName("publish_time_human") val publishTimeHuman: String?,
        @SerializedName("update_time_human") val updateTimeHuman: String?,
        @SerializedName("publish_time_before") val publishTimeBefore: String?,
        @SerializedName("review_id") val reviewId: Int?,
        @SerializedName("title") val title: String?,
        @SerializedName("content") val content: String?,
        @SerializedName("editor_type") val editorType: Int?,
        @SerializedName("editor_content") val editorContent: String?,
        @SerializedName("comment_count") val commentCount: Int?,
        @SerializedName("vote_count") val voteCount: Int?,
        @SerializedName("neat_content") val neatContent: ContentModel?,
        @SerializedName("update_time_show") val updateTimeShow: String?,
        @SerializedName("share_option") val shareOption: Int?,
        @SerializedName("has_vote") val hasVote: Int?,
        @SerializedName("user") val user: SimpleUserModel?,
        @SerializedName("is_wildrose") val isWildrose: Int?
) : BaseModel()

