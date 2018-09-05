package cn.imrhj.cowlevel.network.model

/**
 * Created by rhj on 2017/12/4.
 */
data class ApiModel<out T : BaseModel>(val ec: Int, val em: String, val data: T) : BaseModel()