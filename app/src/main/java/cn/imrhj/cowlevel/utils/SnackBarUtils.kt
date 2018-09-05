package cn.imrhj.cowlevel.utils

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View

/**
 * SnackBar工具类
 * Created by rhj on 15/03/2018.
 */
class SnackBarUtils private constructor(view: View) {
    private val mView = view
    private lateinit var mContent: CharSequence
    private var mDuration: Int = Snackbar.LENGTH_SHORT
    private lateinit var mSnackbar: Snackbar

    private val color_info = -0xdf6b0d
    private val color_confirm = -0xb34fb2
    private val color_warning = -0x13ffb
    private val color_danger = -0xbbcca

    companion object {
        fun with(view: View): SnackBarUtils {
            return SnackBarUtils(view)
        }
    }


    /**
     * 设置内容以及时长
     * @param content 展示内容
     */
    fun short(content: CharSequence): SnackBarUtils {
        mContent = content
        mDuration = Snackbar.LENGTH_SHORT
        generateSnackbar()
        return this
    }

    /**
     * 设置内容以及时长
     * @param content 展示内容
     */
    fun long(content: CharSequence): SnackBarUtils {
        mContent = content
        mDuration = Snackbar.LENGTH_LONG
        generateSnackbar()
        return this
    }

    /**
     * 设置内容以及时长,不自动隐藏
     * @param content 展示内容
     */
    fun indefinite(content: CharSequence): SnackBarUtils {
        mContent = content
        mDuration = Snackbar.LENGTH_INDEFINITE
        generateSnackbar()
        return this
    }

    /**
     * 设置标签及点击回调
     * @param text 按钮点击事件
     * @param listener 回调监听器
     */
    fun setAction(text: CharSequence, listener: View.OnClickListener): SnackBarUtils {
        mSnackbar.setAction(text, listener)
        return this
    }

    /**
     * 设置标签及点击回调
     * @param text 按钮点击事件
     * @param listener 回调监听器
     */
    fun setAction(text: CharSequence, listener: ((view: View) -> Unit)): SnackBarUtils {
        mSnackbar.setAction(text, listener)
        return this
    }

    /**
     * 设置标签及点击回调
     * @see SnackBarUtils.setAction
     * @param text 按钮点击事件
     * @param listener 回调监听器
     */
    fun setAction(@StringRes resId: Int, listener: View.OnClickListener): SnackBarUtils {
        return setAction(mView.resources.getString(resId), listener)
    }

    /**
     * 设置标签及点击回调
     * @see SnackBarUtils.setAction(CharSequence,((view:View) -> Unit))
     * @param text 按钮点击事件
     * @param listener 回调监听器
     */
    fun setAction(@StringRes resId: Int, listener: ((view: View) -> Unit)): SnackBarUtils {
        return setAction(mView.resources.getString(resId), listener)
    }

    /**
     * 设置背景色为提示信息背景
     */
    fun info(): SnackBarUtils {
        mSnackbar.view.setBackgroundColor(color_info)
        return this
    }

    /**
     * 设置背景色为确认信息背景
     */
    fun confirm(): SnackBarUtils {
        mSnackbar.view.setBackgroundColor(color_confirm)
        return this
    }

    /**
     * 设置背景色
     * @see color_danger
     */
    fun danger(): SnackBarUtils {
        mSnackbar.view.setBackgroundColor(color_danger)
        return this
    }

    /**
     * 设置背景色
     * @see color_warning
     */
    fun warning(): SnackBarUtils {
        mSnackbar.view.setBackgroundColor(color_warning)
        return this
    }

    fun show() {
        mSnackbar.show()
    }

    private fun generateSnackbar() {
        mSnackbar = Snackbar.make(mView, mContent, mDuration)
    }
}