package cn.imrhj.cowlevel.network.model.feed

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.ContentModel

/**
 * Created by rhj on 2017/12/4.
 * id : 1841768
 * title : 为什么《传说之下 Undertale》评价很高？
 * comment_count : 0
 * answer_count : 12
 * follower_count : 17
 * is_follow : 0
 * has_collect : 0
 * brief_content : {"desc":"突然想到这个游戏，当时抬手就去游戏页面下给了一颗星，没错这游戏只玩了半个小时就申请退款了。 然后又突然想到几个问题。 1.如果没有媒体和他人的安利（不知道这游戏 ...","thumb":""}
 * neat_content : {"desc":"突然想到这个游戏，当时抬手就去游戏页面下给了一颗星，没错这游戏只玩了半个小时就申请退款了。 然后又突然想到几个问题。 1.如果没有媒体和他人的安利（不知道这游戏 ...","thumb":""}
 * my_answer : []
 */
data class FeedQuestionModel(
        val id: Int = 0,
        val title: String? = null,
        val comment_count: Int = 0,
        val answer_count: Int = 0,
        val follower_count: Int = 0,
        val is_follow: Int = 0,
        val content: String? = null,
        val has_collect: Int = 0,
        val brief_content: ContentModel? = null,
        val neat_content: ContentModel? = null,
        val my_answer: List<*>? = null
) : BaseModel()