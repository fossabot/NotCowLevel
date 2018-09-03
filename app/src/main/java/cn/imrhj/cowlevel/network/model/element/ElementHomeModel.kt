package cn.imrhj.cowlevel.network.model.element

import cn.imrhj.cowlevel.network.model.BaseModel

data class ElementHomeModel(var element: ElementModel? = null, var related: ElementRelatedModel? = null) : BaseModel()