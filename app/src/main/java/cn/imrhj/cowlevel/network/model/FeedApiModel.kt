package cn.imrhj.cowlevel.network.model

/**
 * Created by rhj on 2017/12/4.
 */
data class FeedApiModel(val first_id: String, val last_id: Int, val has_more: Int,
                        val timestamp: Int, val list: List<FeedModel>? = null) : BaseModel()