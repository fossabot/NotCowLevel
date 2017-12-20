package cn.imrhj.cowlevel.network.model

import com.google.gson.annotations.SerializedName

data class UserSiteModel(

        @field:SerializedName("zhihu")
        val zhihu: String? = null,

        @field:SerializedName("github")
        val github: String? = null,

        @field:SerializedName("twitch")
        val twitch: String? = null,

        @field:SerializedName("blizzard")
        val blizzard: String? = null,

        @field:SerializedName("origin")
        val origin: String? = null,

        @field:SerializedName("nintendo_wiiu")
        val nintendoWiiu: String? = null,

        @field:SerializedName("psn")
        val psn: String? = null,

        @field:SerializedName("bilibili")
        val bilibili: String? = null,

        @field:SerializedName("xbox")
        val xbox: String? = null,

        @field:SerializedName("uplay")
        val uplay: String? = null,

        @field:SerializedName("douban")
        val douban: String? = null,

        @field:SerializedName("douyu")
        val douyu: String? = null,

        @field:SerializedName("weibo")
        val weibo: String? = null,

        @field:SerializedName("user_link")
        val userLink: String? = null,

        @field:SerializedName("acfun")
        val acfun: String? = null,

        @field:SerializedName("steam")
        val steam: String? = null,

        @field:SerializedName("nintendo_3ds")
        val nintendo3ds: String? = null,

        @field:SerializedName("nintendo_ns")
        val nintendoNs: String? = null,

        @field:SerializedName("gog")
        val gog: String? = null
) : BaseModel()