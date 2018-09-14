package cn.imrhj.cowlevel.network.model.element

import com.google.gson.annotations.SerializedName

data class TagsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("pic")
	val pic: String? = null,

	@field:SerializedName("follower_count")
	val followerCount: Int? = null,

	@field:SerializedName("content")
	val content: String? = null
)