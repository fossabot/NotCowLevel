package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class PriceInfoModel(
        @field:SerializedName("price_off")
        val priceOff: String? = null,

        @field:SerializedName("cny_price")
        val cnyPrice: String? = null
) : BaseModel()