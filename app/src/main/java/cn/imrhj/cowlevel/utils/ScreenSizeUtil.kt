package cn.imrhj.cowlevel.utils

import cn.imrhj.cowlevel.App

/**
 * Created by rhj on 2017/12/6.
 */
object ScreenSizeUtil {
    private val mDisplayMetrics = App.app.resources.displayMetrics
    fun dp2px(dpValue: Int): Int {
        val scale = mDisplayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dp2px(dpValue: Float): Int {
        val scale = mDisplayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     * @return PX 屏幕宽
     */
    fun getScreenWidth(): Int {
        return mDisplayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度
     * @return PX 屏幕高
     */
    fun getScreenHeight(): Int {
        return mDisplayMetrics.heightPixels
    }
}
