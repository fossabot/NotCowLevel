package cn.imrhj.cowlevel.network.model.common

import cn.imrhj.cowlevel.network.model.BaseModel

/**
 * Created by rhj on 2017/12/4.
 */
data class ContentModel(
        val desc: String? = null,
        val thumb: String? = null
) : BaseModel()