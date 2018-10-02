package cn.imrhj.cowlevel.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import com.airbnb.deeplinkdispatch.DeepLink
import io.reactivex.Observer

/**
 * Created by rhj on 11/12/2017.
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = layoutId()
        if (id != null) {
            setContentView(id)
        }
        initData()
        initView()
        if (!isFromDeepLink()) {
            initViewAfter()
        }
    }

    @LayoutRes
    abstract fun layoutId(): Int?

    open fun initData() {

    }

    open fun initView() {

    }

    open fun initViewAfter() {

    }

    fun callEndTransitionListener(callback: () -> Unit): Transition.TransitionListener {
        return object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                callback()
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

        }
    }

    fun isFromDeepLink(): Boolean {
        return intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)
    }

}