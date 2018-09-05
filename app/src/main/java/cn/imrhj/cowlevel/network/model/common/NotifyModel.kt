package cn.imrhj.cowlevel.network.model.common

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class NotifyModel(
        @field:SerializedName("inbox_count")
        val inboxCount: Int? = null,

        @field:SerializedName("count")
        val count: Int? = null,

        @field:SerializedName("msg_count")
        val msgCount: MsgCountModel? = null
) : BaseModel()