package cn.imrhj.cowlevel.utils

import android.os.Looper

object ThreadUtils {
    fun isMainThread(): Boolean {
        return Looper.getMainLooper() == Looper.myLooper()
    }
}