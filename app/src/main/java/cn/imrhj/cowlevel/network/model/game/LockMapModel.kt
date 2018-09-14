package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class LockMapModel(

        @field:SerializedName("summary")
        val summary: Int? = null,

        @field:SerializedName("feature_image")
        val featureImage: Int? = null,

        @field:SerializedName("discover")
        val discover: Int? = null,

        @field:SerializedName("extensive_urls")
        val extensiveUrls: Int? = null,

        @field:SerializedName("head_pic")
        val headPic: Int? = null,

        @field:SerializedName("language")
        val language: Int? = null,

        @field:SerializedName("pic")
        val pic: Int? = null,

        @field:SerializedName("title")
        val title: Int? = null,

        @field:SerializedName("type")
        val type: Int? = null,

        @field:SerializedName("content")
        val content: Int? = null,

        @field:SerializedName("steam_support")
        val steamSupport: Int? = null,

        @field:SerializedName("platforms")
        val platforms: Int? = null,

        @field:SerializedName("cover")
        val cover: Int? = null,

        @field:SerializedName("resource_urls")
        val resourceUrls: Int? = null,

        @field:SerializedName("play_type")
        val playType: Int? = null,

        @field:SerializedName("series")
        val series: Int? = null,

        @field:SerializedName("platform_support")
        val platformSupport: Int? = null,

        @field:SerializedName("publisher")
        val publisher: Int? = null,

        @field:SerializedName("developer")
        val developer: Int? = null,

        @field:SerializedName("feature_video")
        val featureVideo: Int? = null,

        @field:SerializedName("publish_date")
        val publishDate: Int? = null
) : BaseModel()