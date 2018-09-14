package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class UrlsItemModel(
        @field:SerializedName("goto")
        val jsonMemberGoto: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("url")
        val url: String? = null
) : BaseModel()