package cn.imrhj.cowlevel.extensions

import android.os.Bundle
import android.support.v4.app.Fragment
import cn.imrhj.cowlevel.ui.base.BaseFragment

/**
 * 函数扩展中心
 * Created by rhj on 2017/11/28.
 */

fun <T : BaseFragment> Fragment.newInstance(fc: Class<T>, bundle: Bundle): T {
    val instance = fc.newInstance()
    instance.onConfigFragment(bundle)
    return instance
}
