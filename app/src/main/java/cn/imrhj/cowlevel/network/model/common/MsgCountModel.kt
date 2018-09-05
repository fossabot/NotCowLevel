package cn.imrhj.cowlevel.network.model.common

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class MsgCountModel(
        @field:SerializedName("comment")
        val comment: Int? = null,

        @field:SerializedName("follow")
        val follow: Int? = null,

        @field:SerializedName("vote")
        val vote: Int? = null
) : BaseModel()