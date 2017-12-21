package cn.imrhj.cowlevel.network.model

import com.google.gson.Gson

/**
 * Created by rhj on 2017/12/4.
 */
open class BaseModel {
    fun toJsonString(): String {
        return Gson().toJson(this)
    }

    open fun getType(): Int {
        return 0
    }
}