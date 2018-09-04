package cn.imrhj.cowlevel.network.model.list

import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.FollowedTagNewModel


data class FollowedTagNewListModel(val list: List<FollowedTagNewModel>) : BaseModel() {
    override fun getType(): ItemTypeEnum {
        return ItemTypeEnum.TYPE_HEADER_TAG
    }
}