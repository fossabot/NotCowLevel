package cn.imrhj.cowlevel.network.parse

import cn.imrhj.cowlevel.network.model.FollowedPostNewModel
import cn.imrhj.cowlevel.network.model.FollowedTagNewModel
import cn.imrhj.cowlevel.network.model.element.ElementHomeModel
import cn.imrhj.cowlevel.network.model.game.GameHomeModel
import cn.imrhj.cowlevel.network.model.home.BannerModel
import cn.imrhj.cowlevel.network.model.home.FeedHomeModel
import cn.imrhj.cowlevel.network.model.list.BannerListModel
import cn.imrhj.cowlevel.network.model.list.FollowedPostNewListModel
import cn.imrhj.cowlevel.network.model.list.FollowedTagNewListModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()
private inline fun <reified T> getJsonModel(result: MatchResult?): T? {
    return if (result?.groups?.size ?: 0 > 1) {
        gson.fromJson(result?.groups?.get(1)?.value, T::class.java)
    } else null
}

private inline fun <reified T> getJsonListModel(result: MatchResult?): List<T>? {
    return if (result?.groups?.size ?: 0 > 1) {
        gson.fromJson(result?.groups?.get(1)?.value, TypeToken.getParameterized(List::class.java, T::class.java).type)
    } else null
}

val parseHomeJSString: (String) -> FeedHomeModel = {
    val data = FeedHomeModel()
    data.feedData = getJsonModel(Regex("feedData : (.+)\\['list'],").find(it))
    val postList = getJsonListModel<FollowedPostNewModel>(Regex("followed_post_new: (.+),").find(it))
    data.followedPostNews = if (postList != null) FollowedPostNewListModel(postList) else null
    val tagList = getJsonListModel<FollowedTagNewModel>(Regex("followed_tag_new: (.+),").find(it))
    data.followedTagNews = if (tagList != null) FollowedTagNewListModel(tagList) else null
    val bannerList = getJsonListModel<BannerModel>(Regex("banners:(.+),").find(it))
    data.banners = if (bannerList != null) BannerListModel(bannerList) else null
    data
}

val parseElementJSString: (String) -> ElementHomeModel? = {
    val elementHomeModel = ElementHomeModel()
    elementHomeModel.element = getJsonModel(Regex("element:(.+),").find(it))
    elementHomeModel.related = getJsonModel(Regex("related:(.+),").find(it))
    elementHomeModel
}

val parseGameJSString: (String) -> GameHomeModel? = {
    val gameHomeModel = GameHomeModel()
    gameHomeModel.myPostInterest = getJsonModel(Regex("var my_post_interest = (.+)").find(it))
    gameHomeModel.proUsers = getJsonListModel(Regex("var pro_users = (.+])").find(it))
    gameHomeModel.game = getJsonModel(Regex("var game = (.+);").find(it))
    gameHomeModel.commentList = getJsonListModel(Regex("var comment_list = (.+);").find(it))
    gameHomeModel.videos = getJsonListModel(Regex(" {5}videos:(.+]),").find((it)))
    gameHomeModel.imageList = getJsonListModel(Regex(" {5}feature_images:(.+),").find(it))
    gameHomeModel.relatedGames = getJsonListModel(Regex(" {5}related_games:(.+),").find(it))
    gameHomeModel.relatedQuestions = getJsonListModel(Regex(" {5}related_questions:(.+),").find(it))
    gameHomeModel.articles = getJsonListModel(Regex(" {5}articles:(.+),").find(it))
    gameHomeModel.gameContributors = getJsonListModel(Regex(" {5}game_contributors:(.+),").find(it))
    gameHomeModel.indexedCollections = getJsonListModel(Regex(" {5}indexed_collections:(.+),").find(it))
    gameHomeModel
}

