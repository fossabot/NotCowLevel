package cn.imrhj.cowlevel.network.model.home

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.ElementModel

data class ElementHomeModel(val element: List<ElementModel>? = null) : BaseModel()