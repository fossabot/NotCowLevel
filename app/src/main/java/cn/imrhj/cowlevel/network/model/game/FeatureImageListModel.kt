package cn.imrhj.cowlevel.network.model.game

import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.BaseModel

data class FeatureImageListModel(val list: List<FeatureImageModel>?, val photoCount: Int?) : BaseModel() {
    override fun getType(): ItemTypeEnum {
        return ItemTypeEnum.TYPE_GAME_IMAGE
    }
}