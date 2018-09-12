package cn.imrhj.cowlevel.extensions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import cn.imrhj.cowlevel.ui.base.BaseFragment
import cn.imrhj.cowlevel.utils.StringUtils
import okhttp3.Request

/**
 * 函数扩展中心
 * Created by rhj on 2017/11/28.
 */

fun <T : BaseFragment> Fragment.newInstance(fc: Class<T>, bundle: Bundle): T {
    val instance = fc.newInstance()
    instance.onConfigFragment(bundle)
    return instance
}

fun TextView.setTextAndShow(text: String?) {
    if (StringUtils.isNotBlank(text)) {
        this.text = text
        visibility = View.VISIBLE
    }
}

fun Request.toLogString(): String {
    return "Request: method=${method()}, url = ${url()},\nheader=${headers()}" +
            if (body() != null) ",\nbody=${body()}" else ""
}

fun <T> List<T>.getLastOrEmpty(): T? {
    if (this.isNotEmpty()) {
        return this[0]
    }
    return null
}