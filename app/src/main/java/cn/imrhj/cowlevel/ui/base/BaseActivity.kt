package cn.imrhj.cowlevel.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

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
    }

    @LayoutRes
    abstract fun layoutId(): Int?

    open fun initData() {

    }

    open fun initView() {

    }

}