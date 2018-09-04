package cn.imrhj.cowlevel.network.model.feed

import cn.imrhj.cowlevel.network.model.BaseModel

/**
 * Created by rhj on 2017/12/4.
 */
data class FeedApiModel(val first_id: Int, val last_id: Int, val has_more: Int,
                        val timestamp: Int, val list: List<FeedModel>? = null) : BaseModel()