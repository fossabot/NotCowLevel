package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.ContentModel
import cn.imrhj.cowlevel.network.model.common.NameIDModel
import cn.imrhj.cowlevel.network.model.common.TagModel
import cn.imrhj.cowlevel.network.model.common.UrlModel
import com.google.gson.annotations.SerializedName

data class SimpleGameModel(
        @SerializedName("game_publish_date_show") val gamePublishDateShow: String? = null,
        @SerializedName("has_played") val hasPlayed: Int? = null,
        @SerializedName("is_wish") val isWish: Int? = null,
        @SerializedName("played_count") val playedCount: Int? = null,
        @SerializedName("language") val language: List<NameIDModel?>? = null,
        @SerializedName("pic") val pic: String? = null,
        @SerializedName("title") val title: String? = null,
        @SerializedName("game_publish_year") val gamePublishYear: Int? = null,
        @SerializedName("follower_count") val followerCount: Int? = null,
        @SerializedName("content") val content: String? = null,
        @SerializedName("url_slug") val urlSlug: String? = null,
        @SerializedName("urls") val urls: List<UrlModel?>? = null,
        @SerializedName("star_avg") val starAvg: String? = null,
        // 服务端真是善变,如果为空的话返回的是[],如果不为空的话则是个对象{"tag_4":1373385600, "tag_xxx":xxx}
        // 这个代码没法写了
//        @field:SerializedName("publish_time_map")
//        val publishTimeMap: JsonArray,
        @SerializedName("max_discount") val maxDiscount: Int? = null,
        @SerializedName("chinese_title") val chineseTitle: String? = null,
        @SerializedName("game_publish_day") val gamePublishDay: Int? = null,
        @SerializedName("wish_count") val wishCount: Int? = null,
        @SerializedName("game_publish_date_show_pre") val gamePublishDateShowPre: String? = null,
        @SerializedName("platform_support_list") val platformSupportList: List<NameIDModel?>? = null,
        @SerializedName("discover") val discover: String? = null,
        @SerializedName("brief_content") val briefContent: ContentModel? = null,
        @SerializedName("is_follow") val isFollow: Int? = null,
        @SerializedName("has_collect") val hasCollect: Int? = null,
        @SerializedName("game_publish_month") val gamePublishMonth: Int? = null,
        @SerializedName("tags") val tags: List<TagModel?>? = null,
        @SerializedName("game_prices") val gamePrices: List<GamePriceModel?>? = null,
        @SerializedName("play_time_avg") val playTimeAvg: Double? = null,
        @SerializedName("my_post_interest") val myPostInterest: Any? = null
) : BaseModel()