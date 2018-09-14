package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class AliasNamesModel(

        @field:SerializedName("update_time")
        val updateTime: Int? = null,

        @field:SerializedName("type_name")
        val typeName: String? = null,

        @field:SerializedName("post_id")
        val postId: Int? = null,

        @field:SerializedName("create_time")
        val createTime: Int? = null,

        @field:SerializedName("alias")
        val alias: String? = null,

        @field:SerializedName("rank")
        val rank: Int? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("is_title")
        val isTitle: Int? = null,

        @field:SerializedName("type")
        val type: Int? = null
) : BaseModel()