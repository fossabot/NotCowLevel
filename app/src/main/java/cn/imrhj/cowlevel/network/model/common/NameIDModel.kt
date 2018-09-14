package cn.imrhj.cowlevel.network.model.common

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class NameIDModel(
        @field:SerializedName("name")
        val name: String? = null,
        @field:SerializedName("id")
        val id: Int? = null
) : BaseModel()