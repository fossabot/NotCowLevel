package cn.imrhj.cowlevel.network.parse

import cn.imrhj.cowlevel.network.model.FeedApiModel
import cn.imrhj.cowlevel.network.model.FollowedPostNewModel
import cn.imrhj.cowlevel.network.model.FollowedTagNewModel
import cn.imrhj.cowlevel.network.model.element.ElementHomeModel
import cn.imrhj.cowlevel.network.model.element.ElementModel
import cn.imrhj.cowlevel.network.model.element.ElementRelatedModel
import cn.imrhj.cowlevel.network.model.home.FeedHomeModel
import cn.imrhj.cowlevel.network.model.list.FollowedPostNewListModel
import cn.imrhj.cowlevel.network.model.list.FollowedTagNewListModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val parseHomeJSString: (String) -> FeedHomeModel = {
    val data = FeedHomeModel()
    val gson = Gson()
    val feedDataResult = Regex("feedData : (.+)\\['list'],").find(it)?.groups
    if (feedDataResult?.size?.compareTo(2) == 0) {
        data.feedData = gson.fromJson(feedDataResult[1]?.value, FeedApiModel::class.java)
    }
    val followPostResult = Regex("followed_post_new: (.+),").find(it)?.groups
    if (followPostResult?.size?.compareTo(2) == 0) {
        data.followedPostNews = FollowedPostNewListModel(gson.fromJson(followPostResult[1]?.value,
                object : TypeToken<List<FollowedPostNewModel>>() {}.type))
    }
    val followTagResult = Regex("followed_tag_new: (.+),").find(it)?.groups
    if (followTagResult?.size?.compareTo(2) == 0) {
        data.followedTagNews = FollowedTagNewListModel(gson.fromJson(followTagResult[1]?.value,
                object : TypeToken<List<FollowedTagNewModel>>() {}.type))
    }
    data
}

val parseElementJSString: (String) -> ElementHomeModel? = {
    val gson = Gson()
    val elementHomeModel = ElementHomeModel()
    val elementListResult = Regex("element:(.+),").find(it)?.groups
    if (elementListResult?.size ?: 0 > 1) {
        elementHomeModel.element = gson.fromJson(elementListResult?.get(1)?.value, ElementModel::class.java)
    }
    val relatedResult = Regex("related:(.+),").find(it)?.groups
    if (relatedResult?.size ?: 0 > 1) {
        elementHomeModel.related = gson.fromJson(relatedResult?.get(1)?.value, ElementRelatedModel::class.java)
    }
    elementHomeModel
}