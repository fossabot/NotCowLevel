package cn.imrhj.cowlevel.ui.view.loading

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


/**
 * 动画基类
 * Created by rhj on 19/03/2018.
 */
@Suppress("LeakingThis")
abstract class LVBase : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initPaint()
    }

    private var mValueAnimator: ValueAnimator? = null


    fun startAnim() {
        stopAnim()
        startViewAnim(0f, 1f, 500)
    }

    fun startAnim(time: Int) {
        stopAnim()
        startViewAnim(0f, 1f, time.toLong())
    }


    fun stopAnim() {
        if (mValueAnimator != null) {
            clearAnimation()
            mValueAnimator?.repeatCount = 0
            mValueAnimator?.cancel()
            mValueAnimator?.end()
            if (onStopAnim() == 0) {
                mValueAnimator?.repeatCount = 0
                mValueAnimator?.cancel()
                mValueAnimator?.end()
            }
        }
    }

    private fun startViewAnim(startF: Float, endF: Float, time: Long) {
        mValueAnimator = ValueAnimator.ofFloat(startF, endF)
        mValueAnimator?.duration = time
        mValueAnimator?.interpolator = LinearInterpolator()


        mValueAnimator?.repeatCount = setAnimRepeatCount()



        if (ValueAnimator.RESTART == setAnimRepeatMode()) {
            mValueAnimator?.repeatMode = ValueAnimator.RESTART

        } else if (ValueAnimator.REVERSE == setAnimRepeatMode()) {
            mValueAnimator?.repeatMode = ValueAnimator.REVERSE

        }

        mValueAnimator?.addUpdateListener { valueAnimator -> onAnimationUpdate(valueAnimator) }
        mValueAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator) {
                this@LVBase.onAnimationRepeat(animation)
            }
        })
        if (mValueAnimator?.isRunning != true) {
            ainmIsRunning()
            mValueAnimator?.start()
        }
    }


    protected abstract fun initPaint()

    protected abstract fun onAnimationUpdate(valueAnimator: ValueAnimator)

    protected abstract fun onAnimationRepeat(animation: Animator)

    protected abstract fun onStopAnim(): Int

    protected abstract fun setAnimRepeatMode(): Int

    protected abstract fun setAnimRepeatCount(): Int

    protected abstract fun ainmIsRunning()


    fun dip2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun getFontlength(paint: Paint, str: String): Int {
        val rect = Rect()
        paint.getTextBounds(str, 0, str.length, rect)
        return rect.width()
    }

    fun getFontHeight(paint: Paint, str: String): Int {
        val rect = Rect()
        paint.getTextBounds(str, 0, str.length, rect)
        return rect.height()

    }

    fun getFontHeight(paint: Paint): Float {
        val fm = paint.getFontMetrics()
        return fm.descent - fm.ascent
    }
}