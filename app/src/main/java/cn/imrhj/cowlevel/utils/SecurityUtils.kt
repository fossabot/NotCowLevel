package cn.imrhj.cowlevel.utils

import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.utils.cache.ACache
import com.elvishew.xlog.XLog


/**
 * Created by rhj on 13/12/2017.
 */
object SecurityUtils {
    private val mACache = ACache.get(App.app.filesDir)
    private val SEED = "urcrknBpyGfcY8XnLHqkuYBx7oxBYhVv"

    fun getValue(key: String): String? {
        val encryption = mACache.getAsString(key)
        if (StringUtils.isNotBlank(encryption)) {
            try {
                return AESUtils.decrypt(SEED, encryption!!)
            } catch (e: Exception) {
                XLog.t().b().e(e)
            }

        }

        return null
    }

    fun putValue(key: String, value: String) {
        try {
            val encryption = AESUtils.encrypt(SEED, value)
            mACache.put(key, encryption)
        } catch (e: Exception) {
            XLog.t().st(0).b().e(e)
        }

    }
}