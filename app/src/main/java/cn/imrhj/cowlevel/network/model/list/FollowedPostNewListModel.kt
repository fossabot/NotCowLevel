package cn.imrhj.cowlevel.network.model.list

import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.FollowedPostNewModel


data class FollowedPostNewListModel(val list: List<FollowedPostNewModel>) : BaseModel() {

    override fun getType(): ItemTypeEnum {
        return ItemTypeEnum.TYPE_HEADER_POST
    }
}