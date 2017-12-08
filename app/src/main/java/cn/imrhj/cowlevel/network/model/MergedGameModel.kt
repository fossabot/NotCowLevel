package cn.imrhj.cowlevel.network.model

import com.google.gson.annotations.SerializedName

data class MergedGameModel(

        @field:SerializedName("comment_count")
        val commentCount: Int? = null,

        @field:SerializedName("platform_support_list")
        val platformSupportList: List<PlatformSupportModel?>? = null,

        @field:SerializedName("game_publish_date_show_pre")
        val gamePublishDateShowPre: String? = null,

        @field:SerializedName("discover")
        val discover: String? = null,

        @field:SerializedName("game_publish_date_show")
        val gamePublishDateShow: String? = null,

        @field:SerializedName("has_played")
        val hasPlayed: Int? = null,

        @field:SerializedName("brief_content")
        val briefContent: ContentModel? = null,

        @field:SerializedName("is_follow")
        val isFollow: Int? = null,

        @field:SerializedName("has_collect")
        val hasCollect: Int? = null,

        @field:SerializedName("neat_content")
        val neatContent: PlatformSupportModel? = null,

        @field:SerializedName("pic")
        val pic: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("tags")
        val tags: List<TagModel?>? = null,

        @field:SerializedName("url_slug")
        val urlSlug: String? = null,

        @field:SerializedName("chinese_title")
        val chineseTitle: String? = null
)