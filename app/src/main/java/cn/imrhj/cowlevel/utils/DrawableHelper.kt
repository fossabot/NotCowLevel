package cn.imrhj.cowlevel.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import android.widget.ImageView


/**
 * drawable染色工具类
 * Created by rhj on 25/03/2018.
 */
class DrawableHelper {
    constructor(drawable: Drawable) {
        mDrawable = drawable
    }

    constructor(@DrawableRes drawableRes: Int) {
        mDrawableRes = drawableRes
    }

    @ColorRes
    private var mColor: Int = 0
    private var mDrawable: Drawable? = null
    @DrawableRes
    private var mDrawableRes: Int = 0
    private var mWrappedDrawable: Drawable? = null

    fun setColor(@ColorRes colorRes: Int): DrawableHelper {
        mColor = colorRes
        return this
    }

    private fun tint(context: Context) {
        if (mDrawable == null) {
            mDrawable = ContextCompat.getDrawable(context, mDrawableRes)
        }
        if (mDrawable == null) {
            return
        }

        mWrappedDrawable = mDrawable?.mutate()
        if (mWrappedDrawable != null) {
            mWrappedDrawable = DrawableCompat.wrap(mWrappedDrawable!!)
            DrawableCompat.setTint(mWrappedDrawable!!, ContextCompat.getColor(context, mColor))
            DrawableCompat.setTintMode(mWrappedDrawable!!, PorterDuff.Mode.SRC_IN)
        }
    }

    fun applyToBackground(view: View) {
        tint(view.context)
        view.background = mWrappedDrawable
    }

    fun applyTo(imageView: ImageView) {
        tint(imageView.context)
        imageView.setImageDrawable(mWrappedDrawable)
    }

    companion object {
        fun withDrawable(@DrawableRes drawableRes: Int): DrawableHelper {
            return DrawableHelper(drawableRes)
        }

        fun withDrawable(drawable: Drawable): DrawableHelper {
            return DrawableHelper(drawable)
        }
    }
}