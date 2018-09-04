package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.BaseModel
import com.google.gson.annotations.SerializedName

data class ElementRelatedModel(
        @field:SerializedName("parent")
        val parent: List<ElementChildItemModel?>? = null,

        @field:SerializedName("child")
        val child: List<ElementChildItemModel?>? = null
) : BaseModel() {
    override fun getType(): ItemTypeEnum {
        return ItemTypeEnum.TYPE_ELEMENT_RELATED
    }

}