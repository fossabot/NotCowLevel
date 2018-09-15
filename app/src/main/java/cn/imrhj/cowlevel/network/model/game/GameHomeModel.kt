package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.SimpleUserModel
import cn.imrhj.cowlevel.network.model.element.ArticleModel
import cn.imrhj.cowlevel.network.model.element.QuestionModel
import cn.imrhj.cowlevel.network.model.feed.AnswerModel
import cn.imrhj.cowlevel.network.model.feed.CommentModel
import cn.imrhj.cowlevel.network.model.video.VideoModel
import com.google.gson.annotations.SerializedName


data class GameHomeModel(
        var game: GameModel? = null,
        var imageList: List<FeatureImageModel>? = null,
        var gameContributors: List<SimpleUserModel>? = null,
        var proUsers: List<ProUserModel>? = null,
        var myPostInterest: PostInterestModel? = null,
        var relatedQuestions: List<QuestionModel>? = null,
        var videos: List<VideoModel>? = null,
        var articles: List<ArticleModel>? = null,
        var relatedGames: List<RelatedGameModel>? = null,
        var commentList: List<CommentModel>? = null,
        var indexedCollections: List<IndexedCollectionModel>? = null
) : BaseModel() {
    data class ProUserModel(
            @SerializedName("name") val name: String?,
            @SerializedName("avatar") val avatar: String?,
            @SerializedName("intro") val intro: String?,
            @SerializedName("url_slug") val urlSlug: String?,
            @SerializedName("voted_count_total") val votedCountTotal: Int?,
            @SerializedName("most_voted_item") val mostVotedItem: MostVotedItem?,
            @SerializedName("is_follow") val isFollow: Int?,
            @SerializedName("is_follow_by") val isFollowBy: Int?,
            @SerializedName("is_follow_both") val isFollowBoth: Int?,
            @SerializedName("is_block") val isBlock: Int?
    ) {
        data class MostVotedItem(
                @SerializedName("article") val article: ArticleModel?,
                @SerializedName("answer") val answer: AnswerModel?
        )
    }
}