package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class AppearanceConfig(
        @field:SerializedName("show_head_pic")
        val showHeadPic: Int? = null,

        @field:SerializedName("show_cover")
        val showCover: Int? = null,

        @field:SerializedName("cover_display_type")
        val coverDisplayType: Int? = null,

        @field:SerializedName("show_discover")
        val showDiscover: Int? = null,

        @field:SerializedName("head_block_display_type")
        val headBlockDisplayType: Int? = null
) : BaseModel()