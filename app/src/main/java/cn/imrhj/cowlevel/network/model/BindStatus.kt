package cn.imrhj.cowlevel.network.model

import com.google.gson.annotations.SerializedName

data class BindStatus(

	@field:SerializedName("weibo")
	val weibo: Int? = null,

	@field:SerializedName("steam")
	val steam: Int? = null
)