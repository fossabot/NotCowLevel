package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.*
import cn.imrhj.cowlevel.network.model.element.GamePriceModel
import com.google.gson.annotations.SerializedName

data class GameModel(
        @SerializedName("comment_count") val commentCount: Int? = null,
        @SerializedName("is_dislike") val isDislike: Int? = null,
        @SerializedName("has_played") val hasPlayed: Int? = null,
        @SerializedName("type_name") val typeName: String? = null,
        @SerializedName("is_wish") val isWish: Int? = null,
        @SerializedName("permanent_slug") val permanentSlug: String? = null,
        @SerializedName("bad") val bad: Int? = null,
        @SerializedName("wishreview_count") val wishreviewCount: Int? = null,
        @SerializedName("pv") val pv: Int? = null,
        @SerializedName("publisher_list") val publisherList: List<NameIDModel?>? = null,
        @SerializedName("language") val language: List<NameIDModel?>? = null,
        @SerializedName("pic") val pic: String? = null,
        @SerializedName("type") val type: Int? = null,
        @SerializedName("game_publish_year") val gamePublishYear: Int? = null,
        @SerializedName("new_content") val newContent: String? = null,
        @SerializedName("cover") val cover: String? = null,
        @SerializedName("resource_urls") val resourceUrls: List<Any?>? = null,
        @SerializedName("new_time") val newTime: Int? = null,
        @SerializedName("developer_list") val developerList: List<NameIDModel?>? = null,
        @SerializedName("has_comment") val hasComment: Int? = null,
        @SerializedName("star_avg") val starAvg: String? = null,
        @SerializedName("article_count") val articleCount: Int? = null,
        @SerializedName("publish_time") val publishTime: Int? = null,
        @SerializedName("id") val id: Int? = null,
        @SerializedName("lock_map") val lockMap: String? = null,
        @SerializedName("resource_ids") val resourceIds: String? = null,
        @SerializedName("question_answer_count") val questionAnswerCount: Int? = null,
        @SerializedName("editor_type") val editorType: Int? = null,
        @SerializedName("selected") val selected: Int? = null,
        @SerializedName("platform_support_list") val platformSupportList: List<NameIDModel?>? = null,
        @SerializedName("game_publish_date_show_pre") val gamePublishDateShowPre: String? = null,
        @SerializedName("discover") val discover: String? = null,
        @SerializedName("uv") val uv: Int? = null,
        @SerializedName("brief_content") val briefContent: ContentModel? = null,
        @SerializedName("extensive_urls") val extensiveUrls: List<Any?>? = null,
        @SerializedName("create_time") val createTime: Int? = null,
        @SerializedName("has_collect") val hasCollect: Int? = null,
        @SerializedName("head_pic") val headPic: String? = null,
        @SerializedName("extensive_ids") val extensiveIds: String? = null,
        @SerializedName("p_release_date") val pReleaseDate: List<GamePriceModel?>? = null,
        @SerializedName("tags") val tags: List<TagModel?>? = null,
        @SerializedName("question_count") val questionCount: Int? = null,
        @SerializedName("ps_release_date") val psReleaseDate: String? = null,
        @SerializedName("play_time_avg") val playTimeAvg: String? = null,
        @SerializedName("publish_time_before") val publishTimeBefore: String? = null,
        @SerializedName("new_count") val newCount: Int? = null,
        @SerializedName("guide_count") val guideCount: Int? = null,
        @SerializedName("publisher") val publisher: String? = null,
        @SerializedName("publish_time_human") val publishTimeHuman: String? = null,
        @SerializedName("status") val status: Int? = null,
        @SerializedName("game_publish_date_show") val gamePublishDateShow: String? = null,
        @SerializedName("steam_app_id") val steamAppId: Int? = null,
        @SerializedName("is_home") val isHome: Int? = null,
        @SerializedName("base_post_id") val basePostId: Int? = null,
        @SerializedName("lowest_price") val lowestPrice: Int? = null,
        @SerializedName("neat_content") val neatContent: ContentModel? = null,
        @SerializedName("total_star_avg") val totalStarAvg: String? = null,
        @SerializedName("played_count") val playedCount: Int? = null,
        @SerializedName("title") val title: String? = null,
        @SerializedName("follower_count") val followerCount: Int? = null,
        @SerializedName("content") val content: String? = null,
        @SerializedName("url_slug") val urlSlug: String? = null,
        @SerializedName("update_time_human") val updateTimeHuman: String? = null,
        @SerializedName("urls") val urls: List<UrlModel?>? = null,
        @SerializedName("update_time") val updateTime: Int? = null,
        @SerializedName("alias_names") val aliasNames: List<AliasNamesModel?>? = null,
        @SerializedName("contributor") val contributor: String? = null,
        @SerializedName("chinese_title") val chineseTitle: String? = null,
        @SerializedName("rank_info") val rankInfo: List<RankInfoModel?>? = null,
        @SerializedName("appearance_config") val appearanceConfig: AppearanceConfig? = null,
        @SerializedName("game_publish_day") val gamePublishDay: Int? = null,
        @SerializedName("you_followed_played_info") val youFollowedPlayedInfo: YouFollowedPlayedInfo? = null,
        @SerializedName("wish_count") val wishCount: Int? = null,
        @SerializedName("summary") val summary: String? = null,
        @SerializedName("lock_map_array") val lockMapArray: LockMapModel? = null,
        @SerializedName("editor_content") val editorContent: String? = null,
        @SerializedName("is_follow") val isFollow: Int? = null,
        @SerializedName("star") val star: Int? = null,
        @SerializedName("game_publish_month") val gamePublishMonth: Int? = null,
        @SerializedName("dlc_games") val dlcGames: List<Any?>? = null,
        @SerializedName("can_edit") val canEdit: Int? = null,
        @SerializedName("series_list") val seriesList: List<NameIDModel?>? = null,
        @SerializedName("steam_support") val steamSupport: List<NameIDModel?>? = null,
        @SerializedName("mod_games") val modGames: List<Any?>? = null,
        @SerializedName("play_time") val playTime: String? = null,
        @SerializedName("game_prices") val gamePrices: List<GamePriceModel?>? = null,
        @SerializedName("play_type") val playType: List<NameIDModel?>? = null,
        @SerializedName("photo_count") val photoCount: Int? = null,
        @SerializedName("ns_uniq_code") val nsUniqCode: String? = null,
        @SerializedName("series") val series: String? = null,
        @SerializedName("my_post_interest") val myPostInterest: Any? = null,
        @SerializedName("video_count") val videoCount: Int? = null,
        @SerializedName("developer") val developer: String? = null,
        @SerializedName("user") val user: SimpleUserModel? = null,
        @SerializedName("is_developer") val isDeveloper: Int? = null,
        @SerializedName("series_games") val seriesGames: List<SeriesGamesModel?>? = null
) : BaseModel()