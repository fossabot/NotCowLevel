package cn.imrhj.cowlevel.network.model.feed

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.ContentModel
import com.google.gson.annotations.SerializedName

/**
 * Created by rhj on 2017/12/4.
 * id : 2308228
 * comment_count : 0
 * vote_count : 2
 * has_vote : 0
 * brief_content : {"desc":"看了前面大佬们的回答，很强大，分析的很出彩，但是我还是想来这个问题写下我的答案。只针对于我这个小白来说吧，不是很懂游戏，但是这个游戏确实给了我温暖。 今年10 ...","thumb":""}
 * neat_content : {"desc":"看了前面大佬们的回答，很强大，分析的很出彩，但是我还是想来这个问题写下我的答案。只针对于我这个小白来说吧，不是很懂游戏，但是这个游戏确实给了我温暖。 今年10 ...","thumb":""}
 * lock_comment : 0
 * hide_comment : 0
 */
data class AnswerModel(
        val id: Int = 0,
        val comment_count: Int = 0,
        val vote_count: Int = 0,
        val has_vote: Int = 0,
        val brief_content: ContentModel? = null,
        val neat_content: ContentModel? = null,
        val lock_comment: Int = 0,
        val hide_comment: Int = 0,
        @SerializedName("question_id") val questionId: Int?,
        @SerializedName("question_title") val questionTitle: String?
) : BaseModel()