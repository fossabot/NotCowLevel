package cn.imrhj.cowlevel.network.model.list

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.FollowedPostNewModel

const val TYPE_HEADER_POST = 3

data class FollowedPostNewListModel(val list: List<FollowedPostNewModel>) : BaseModel() {

    override fun getType(): Int {
        return TYPE_HEADER_POST
    }
}