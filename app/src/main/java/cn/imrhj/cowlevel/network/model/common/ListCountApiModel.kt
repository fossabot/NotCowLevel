package cn.imrhj.cowlevel.network.model.common

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class ListCountApiModel<T>(
        @field:SerializedName("list")
        val list: List<T>,
        @field:SerializedName("total_count")
        val totalCount: Int,
        @field:SerializedName("total_page")
        val totalPage: Int,
        @field:SerializedName("has_more")
        val hasMore: Int
) : BaseModel()