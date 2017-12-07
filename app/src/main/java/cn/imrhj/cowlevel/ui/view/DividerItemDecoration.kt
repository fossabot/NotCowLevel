package cn.imrhj.cowlevel.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.support.v4.content.ContextCompat


/**
 * Created by rhj on 2017/12/6.
 */

class DividerItemDecoration
/**
 * Custom divider will be used
 */(context: Context, resId: Int) : RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider!!.intrinsicHeight

            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(c)
        }
    }

    init {
        divider = ContextCompat.getDrawable(context, resId)
    }
}
