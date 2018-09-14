package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class RankInfoModel(
        @field:SerializedName("tag_name")
        val tagName: String? = null,

        @field:SerializedName("tag_id")
        val tagId: Int? = null,

        @field:SerializedName("rank")
        val rank: Int? = null,

        @field:SerializedName("played_count")
        val playedCount: Int? = null,

        @field:SerializedName("percent")
        val percent: Int? = null
) : BaseModel()