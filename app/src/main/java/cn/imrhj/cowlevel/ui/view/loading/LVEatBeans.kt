package cn.imrhj.cowlevel.ui.view.loading

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet


/**
 * 吃豆人动画
 * Created by rhj on 19/03/2018.
 */

class LVEatBeans : LVBase {

    private lateinit var mPaint: Paint
    private lateinit var mPaintEye: Paint

    private var mWidth = 0f
    private var mHigh = 0f
    private val mPadding = 5f

    private val eatErWidth = 60f
    private var eatErPositonX = 0f
    private var eatSpeed = 5
    private val beansWidth = 10f


    private val mAngle = 34f
    private var eatErStrtAngle = mAngle
    private var eatErEndAngle = 360 - 2 * eatErStrtAngle

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mWidth = measuredWidth.toFloat()
        mHigh = measuredHeight.toFloat()
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val eatRightX = mPadding + eatErWidth + eatErPositonX
        val rectF = RectF(mPadding + eatErPositonX, mHigh / 2 - eatErWidth / 2, eatRightX,
                mHigh / 2 + eatErWidth / 2)
        canvas.drawArc(rectF, eatErStrtAngle, eatErEndAngle, true, mPaint)
        canvas.drawCircle(mPadding + eatErPositonX + eatErWidth / 2,
                mHigh / 2 - eatErWidth / 4,
                beansWidth / 2, mPaintEye)

        val beansCount = ((mWidth - mPadding * 2 - eatErWidth) / beansWidth / 2f).toInt()
        for (i in 0 until beansCount) {

            val x = (beansCount * i).toFloat() + beansWidth / 2 + mPadding + eatErWidth
            if (x > eatRightX) {
                canvas.drawCircle(x,
                        mHigh / 2, beansWidth / 2, mPaint)
            }
        }


    }

    override fun initPaint() {

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.WHITE

        mPaintEye = Paint()
        mPaintEye.isAntiAlias = true
        mPaintEye.style = Paint.Style.FILL
        mPaintEye.color = Color.BLACK
    }


    fun setViewColor(color: Int) {
        mPaint.color = color
        postInvalidate()
    }

    fun setEyeColor(color: Int) {
        mPaintEye.color = color
        postInvalidate()
    }


    override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
        val mAnimatedValue = valueAnimator.animatedValue as Float
        eatErPositonX = (mWidth - 2 * mPadding - eatErWidth) * mAnimatedValue
        eatErStrtAngle = mAngle * (1 - (mAnimatedValue * eatSpeed - (mAnimatedValue * eatSpeed).toInt()))
        eatErEndAngle = 360 - eatErStrtAngle * 2
        invalidate()
    }

    override fun onAnimationRepeat(animation: Animator) {

    }

    override fun onStopAnim(): Int {
        eatErPositonX = 0f
        postInvalidate()
        return 1
    }

    override fun setAnimRepeatMode(): Int {
        return ValueAnimator.RESTART
    }

    override fun ainmIsRunning() {

    }

    override fun setAnimRepeatCount(): Int {
        return ValueAnimator.INFINITE
    }
}