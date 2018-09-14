package cn.imrhj.cowlevel.network.model.element

import com.google.gson.annotations.SerializedName

data class GamePriceModel(

        @field:SerializedName("data")
        val data: PriceInfoModel? = null,

        @field:SerializedName("tag")
        val tag: Int? = null
)