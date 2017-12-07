package cn.imrhj.cowlevel.utils

import cn.imrhj.cowlevel.App

/**
 * Created by rhj on 2017/12/6.
 */

fun dp2px(dpValue: Float): Int {
    val scale = App.getApplication().resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}