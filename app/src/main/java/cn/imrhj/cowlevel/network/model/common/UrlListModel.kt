package cn.imrhj.cowlevel.network.model.common

import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.BaseModel

data class UrlListModel(val list: List<UrlModel?>) : BaseModel() {
    override fun getType(): ItemTypeEnum {
        return ItemTypeEnum.TYPE_URLS
    }
}