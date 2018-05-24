package cn.imrhj.cowlevel.ui.animate.listener

import android.animation.Animator

fun callEndAnimatorListener(func: () -> Unit): Animator.AnimatorListener {
    return object : SimpleAnimatorListener() {
        override fun onAnimationEnd(animation: Animator?) {
            func()
        }
    }
}

object AnimatorUtils {
}