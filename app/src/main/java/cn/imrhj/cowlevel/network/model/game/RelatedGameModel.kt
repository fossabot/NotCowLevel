package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.NameIDModel
import cn.imrhj.cowlevel.network.model.common.TagModel
import cn.imrhj.cowlevel.network.model.common.UrlModel
import cn.imrhj.cowlevel.network.model.element.GamePriceModel
import com.google.gson.annotations.SerializedName


data class RelatedGameModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("title") val title: String?,
        @SerializedName("chinese_title") val chineseTitle: String?,
        @SerializedName("content") val content: String?,
        @SerializedName("url_slug") val urlSlug: String?,
        @SerializedName("permanent_slug") val permanentSlug: String?,
        @SerializedName("pic") val pic: String?,
        @SerializedName("cover") val cover: String?,
        @SerializedName("discover") val discover: String?,
        @SerializedName("game_publish_year") val gamePublishYear: Int?,
        @SerializedName("game_publish_month") val gamePublishMonth: Int?,
        @SerializedName("game_publish_day") val gamePublishDay: Int?,
        @SerializedName("follower_count") val followerCount: Int?,
        @SerializedName("wish_count") val wishCount: Int?,
        @SerializedName("played_count") val playedCount: Int?,
        @SerializedName("star_avg") val starAvg: String?,
        @SerializedName("play_time_avg") val playTimeAvg: String?,
        @SerializedName("urls") val urls: List<UrlModel?>?,
        @SerializedName("game_prices") val gamePrices: List<GamePriceModel?>?,
        @SerializedName("game_publish_date_show") val gamePublishDateShow: String?,
        @SerializedName("game_publish_date_show_pre") val gamePublishDateShowPre: String?,
        @SerializedName("platform_support_list") val platformSupportList: List<NameIDModel?>?,
        @SerializedName("my_post_interest") val myPostInterest: Any?,
        @SerializedName("has_played") val hasPlayed: Int?,
        @SerializedName("is_wish") val isWish: Int?,
        @SerializedName("is_follow") val isFollow: Int?,
        @SerializedName("has_collect") val hasCollect: Int?,
        @SerializedName("is_dislike") val isDislike: Int?,
        @SerializedName("tags") val tags: List<TagModel?>?
) : BaseModel()