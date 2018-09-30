package cn.imrhj.cowlevel.extensions

import android.arch.lifecycle.LifecycleOwner
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.TextView
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.network.model.game.GameHomeModel
import cn.imrhj.cowlevel.ui.base.BaseFragment
import cn.imrhj.cowlevel.utils.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
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

fun Fragment.getColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(context ?: App.app, id)
}

fun String.parseHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(this, 0) else
        Html.fromHtml(this)
}

/**
 * 添加非空检查的添加函数
 */
fun <T> BaseQuickAdapter<T, BaseViewHolder>.addNullableData(data: T) {
    if (data != null) {
        addData(data)
    }
}

inline fun <S, T : S> Iterable<T>.reduceNullable(operation: (acc: S, T) -> S): S? {
    if (this.count() <= 0) {
        return null
    }
    return this.reduce(operation)
}

fun <T> Observable<T>.bindLifecycle(lifecycle: LifecycleOwner): ObservableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable<T>(AndroidLifecycleScopeProvider.from(lifecycle)))
}