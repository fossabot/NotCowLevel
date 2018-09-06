package cn.imrhj.cowlevel.ui.base

import android.os.Bundle
import com.elvishew.xlog.XLog


/**
 * 支持懒加载的Fragment
 * Created by rhj on 2017/11/28.
 */
abstract class LazyLoadFragment : BaseFragment() {
    private var isViewInitiated: Boolean = false
    private var isDataLoaded: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        isDataLoaded = false
        prepareRequestData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        prepareRequestData()
    }

    abstract fun requestData()

    private fun prepareRequestData(): Boolean {
        return prepareRequestData(false)
    }

    private fun prepareRequestData(forceUpdate: Boolean): Boolean {
        if (userVisibleHint && isViewInitiated && (!isDataLoaded || forceUpdate)) {
            requestData()
            isDataLoaded = true
            return true
        }
        return false
    }
}