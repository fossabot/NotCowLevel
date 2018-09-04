package cn.imrhj.cowlevel.network.model.home

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.feed.FeedApiModel
import cn.imrhj.cowlevel.network.model.list.FollowedPostNewListModel
import cn.imrhj.cowlevel.network.model.list.FollowedTagNewListModel

data class FeedHomeModel(var feedData: FeedApiModel? = null,
                         var followedPostNews: FollowedPostNewListModel? = null,
                         var followedTagNews: FollowedTagNewListModel? = null
) : BaseModel()