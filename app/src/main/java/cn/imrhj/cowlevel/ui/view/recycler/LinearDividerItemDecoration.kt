package cn.imrhj.cowlevel.ui.view.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout


class LinearDividerItemDecoration(context: Context, orientation: Int, @DrawableRes id: Int,
                                  showFirstLine: Boolean = false, showLastLine: Boolean = true)
    : RecyclerView.ItemDecoration() {
    val HORIZONTAL = LinearLayout.HORIZONTAL
    val VERTICAL = LinearLayout.VERTICAL

    private val TAG = "DividerItem"
    private val ATTRS = intArrayOf(android.R.attr.listDivider)
    private var mDivider: Drawable
    private var mShowFirstLine = showFirstLine
    private var mShowLastLine = showLastLine

    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL].
     */
    private var mOrientation: Int = 0

    private val mBounds = Rect()

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        a.recycle()
        mDivider = context.getDrawable(id)
                ?: throw NullPointerException("Drawable can't bee null! May be you set a null Drawable ID")
        setOrientation(orientation)
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * [RecyclerView.LayoutManager] changes orientation.
     *
     * @param orientation [.HORIZONTAL] or [.VERTICAL]
     */
    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL")
        }
        mOrientation = orientation
    }

    /**
     * Sets the [Drawable] for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    fun setDrawable(drawable: Drawable) {
        mDivider = drawable
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (parent.layoutManager == null) {
            return
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if ((mShowLastLine || (i < childCount - 1 && i > 0)) || (mShowFirstLine && i == 0)) {
                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + Math.round(child.translationY)
                val top = bottom - (mDivider.intrinsicHeight ?: 0)
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(canvas)
            }
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top,
                    parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if ((mShowLastLine || (i < childCount - 1 && i > 0)) || (mShowFirstLine && i == 0)) {
                val child = parent.getChildAt(i)
                parent.layoutManager.getDecoratedBoundsWithMargins(child, mBounds)
                val right = mBounds.right + Math.round(child.translationX)
                val left = right - mDivider.intrinsicWidth
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(canvas)
            }
        }
        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        var count = parent.adapter.itemCount - 1
        count += if (mShowLastLine) 1 else 0
        count += if (mShowFirstLine) 1 else 0
        val position = parent.getChildAdapterPosition(view)
        if (mOrientation == VERTICAL) {
            val spanSpace = mDivider.intrinsicHeight
            if (mShowFirstLine) {
                if (position < count) {
                    if (position == 0) {
                        outRect.set(0, spanSpace, 0, spanSpace)
                    } else {
                        outRect.set(0, 0, 0, spanSpace)
                    }
                } else {
                    outRect.set(0, 0, 0, 0)
                }
            } else {
                if (position < count) {
                    outRect.set(0, 0, 0, spanSpace)
                } else {
                    outRect.set(0, 0, 0, 0)
                }
            }
        } else {
            val spanSpace = mDivider.intrinsicHeight
            if (mShowFirstLine) {
                if (position < count) {
                    if (position == 0) {
                        outRect.set(spanSpace, 0, spanSpace, 0)
                    } else {
                        outRect.set(0, 0, spanSpace, 0)
                    }
                } else {
                    outRect.set(0, 0, 0, 0)
                }
            } else {
                if (position < count) {
                    outRect.set(0, 0, spanSpace, 0)
                } else {
                    outRect.set(0, 0, 0, 0)
                }
            }
        }
    }
}