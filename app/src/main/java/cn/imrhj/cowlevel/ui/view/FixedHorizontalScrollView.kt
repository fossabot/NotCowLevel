package cn.imrhj.cowlevel.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView

class FixedHorizontalScrollView : HorizontalScrollView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mCanScroll: Boolean = true
    private var mDowX: Float = 0f
    private var mLastX: Float = 0f


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            mDowX = ev.x
        }
        if (ev?.action == MotionEvent.ACTION_MOVE) {
            if ((scrollX == 0 && mDowX < ev.x) || (scrollX + measuredWidth == getChildAt(0).measuredWidth && mDowX > ev.x)) {
                mCanScroll = false
            }
            if (ev.x > mLastX && scrollX + measuredWidth == getChildAt(0).measuredWidth) {
                mCanScroll = true
            }
        }

        if (ev?.action == MotionEvent.ACTION_UP || ev?.action == MotionEvent.ACTION_CANCEL) {
            mCanScroll = true
        }
        mLastX = ev?.x ?: 0f
        return if (mCanScroll) {
            super.dispatchTouchEvent(ev)
        } else {
            false
        }

    }
}