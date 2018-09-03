package cn.imrhj.cowlevel.network.model.list

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.FollowedTagNewModel

const val TYPE_HEADER_TAG = 4

data class FollowedTagNewListModel(val list: List<FollowedTagNewModel>) : BaseModel() {
    override fun getType(): Int {
        return TYPE_HEADER_TAG
    }
}