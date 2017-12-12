package cn.imrhj.cowlevel.utils

import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.res.ResourcesCompat
import cn.imrhj.cowlevel.App

/**
 * Created by rhj on 12/12/2017.
 */
object ResourceUtil {
    private val res = App.getAppContext().resources

    @ColorInt
    fun getColor(@ColorRes id: Int): Int {
        return ResourcesCompat.getColor(res, id, null)
    }

    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ResourcesCompat.getDrawable(res, id, null)
    }

}
