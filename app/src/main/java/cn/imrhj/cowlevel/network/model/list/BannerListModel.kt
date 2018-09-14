package cn.imrhj.cowlevel.network.model.list

import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.home.BannerModel

data class BannerListModel(val list: List<BannerModel>) : BaseModel() {
    override fun getType(): ItemTypeEnum {
        return ItemTypeEnum.TYPE_BANNER
    }
}