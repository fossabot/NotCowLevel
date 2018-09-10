package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.common.ContentModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class GameModel(

        @field:SerializedName("game_publish_date_show")
        val gamePublishDateShow: String? = null,

        @field:SerializedName("has_played")
        val hasPlayed: Int? = null,

        @field:SerializedName("is_wish")
        val isWish: Int? = null,

        @field:SerializedName("played_count")
        val playedCount: Int? = null,

        @field:SerializedName("language")
        val language: List<NameIDModel?>? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("game_publish_year")
        val gamePublishYear: Int? = null,

        @field:SerializedName("follower_count")
        val followerCount: Int? = null,

        @field:SerializedName("content")
        val content: String? = null,

        @field:SerializedName("url_slug")
        val urlSlug: String? = null,

        @field:SerializedName("urls")
        val urls: List<UrlsItemModel?>? = null,

        @field:SerializedName("star_avg")
        val starAvg: String? = null,

//        @field:SerializedName("publish_time_map")
//        val publishTimeMap: JsonArray,

        @field:SerializedName("max_discount")
        val maxDiscount: Int? = null,

        @field:SerializedName("chinese_title")
        val chineseTitle: String? = null,

        @field:SerializedName("game_publish_day")
        val gamePublishDay: Int? = null,

        @field:SerializedName("wish_count")
        val wishCount: Int? = null,

        @field:SerializedName("game_publish_date_show_pre")
        val gamePublishDateShowPre: String? = null,

        @field:SerializedName("platform_support_list")
        val platformSupportList: List<NameIDModel?>? = null,

        @field:SerializedName("discover")
        val discover: String? = null,

        @field:SerializedName("brief_content")
        val briefContent: ContentModel? = null,

        @field:SerializedName("is_follow")
        val isFollow: Int? = null,

        @field:SerializedName("has_collect")
        val hasCollect: Int? = null,

        @field:SerializedName("game_publish_month")
        val gamePublishMonth: Int? = null,

        @field:SerializedName("tags")
        val tags: List<TagsItem?>? = null,

        @field:SerializedName("game_prices")
        val gamePrices: List<GamePricesItemModel?>? = null,

        @field:SerializedName("play_time_avg")
        val playTimeAvg: Double? = null,

        @field:SerializedName("my_post_interest")
        val myPostInterest: Any? = null
)