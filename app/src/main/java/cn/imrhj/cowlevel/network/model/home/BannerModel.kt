package cn.imrhj.cowlevel.network.model.home

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class BannerModel(
        @field:SerializedName("create_time")
        val createTime: Int? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("content")
        val content: String? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("update_time")
        val updateTime: Int? = null,

        @field:SerializedName("goto")
        val jsonMemberGoto: String? = null,

        @field:SerializedName("small_pic")
        val smallPic: String? = null,

        @field:SerializedName("end")
        val end: Int? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("position")
        val position: Int? = null,

        @field:SerializedName("begin")
        val begin: Int? = null,

        @field:SerializedName("status")
        val status: Int? = null
) : BaseModel()