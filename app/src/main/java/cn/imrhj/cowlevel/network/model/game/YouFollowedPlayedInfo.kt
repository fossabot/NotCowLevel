package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class YouFollowedPlayedInfo(
        @field:SerializedName("play_time_avg")
        val playTimeAvg: Int? = null,

        @field:SerializedName("total")
        val total: Int? = null,

        @field:SerializedName("star_avg")
        val starAvg: Int? = null,

        @field:SerializedName("users")
        val users: List<Any?>? = null
) : BaseModel()