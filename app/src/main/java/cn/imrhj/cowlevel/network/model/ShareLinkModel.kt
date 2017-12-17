package cn.imrhj.cowlevel.network.model

import com.google.gson.annotations.SerializedName

data class ShareLinkModel(

	@field:SerializedName("origin_url")
	val originUrl: String? = null,

	@field:SerializedName("comment_count")
	val commentCount: Int? = null,

	@field:SerializedName("has_rec")
	val hasRec: Int? = null,

	@field:SerializedName("pic")
	val pic: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("url_md5")
	val urlMd5: String? = null,

	@field:SerializedName("domain")
	val domain: String? = null,

	@field:SerializedName("custom_title")
	val customTitle: String? = null,

	@field:SerializedName("lock_comment")
	val lockComment: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("rec_count")
	val recCount: Int? = null,

	@field:SerializedName("hide_comment")
	val hideComment: Int? = null,

	@field:SerializedName("publish_time_human")
	val publishTimeHuman: String? = null
)