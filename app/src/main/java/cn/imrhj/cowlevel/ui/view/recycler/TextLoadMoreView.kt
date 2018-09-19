package cn.imrhj.cowlevel.ui.view.recycler

import cn.imrhj.cowlevel.R
import com.chad.library.adapter.base.loadmore.LoadMoreView

class TextLoadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.recycler_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_end_view
    }
}