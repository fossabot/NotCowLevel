package cn.imrhj.cowlevel.ui.base

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.ui.view.SmoothLinearLayoutManager
import cn.imrhj.cowlevel.utils.CollectionUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_BOTTOM
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.elvishew.xlog.XLog


/**
 * 拥有下拉刷新，上拉加载的Fragment基类
 * Created by rhj on 2017/12/9.
 */
abstract class RecyclerFragment<T> : LazyLoadFragment() {
    private var mRecycler: RecyclerView? = null
    private var mRefresh: SwipeRefreshLayout? = null
    private var mAdapter: BaseQuickAdapter<T, BaseViewHolder>? = null
    open var mOnComplete: () -> Unit = { this.onComplete() }
    open var mOnError: (t: Throwable) -> Unit = { this.onError(it) }

    /**
     * 是否还有更多数据
     */
    private var mHasMore: Boolean = false
    /**
     * 是否正在加载更多
     */
    private var mIsShowNext: Boolean = false

    private var mNextCursor: Int = 0

    open var mFirstLoaded = false

    override fun initView(baseView: View?) {
        mRecycler = baseView?.findViewById(R.id.recycler)
        mRefresh = baseView?.findViewById(R.id.refresh)
        mRecycler?.layoutManager = getLayoutManager()
        mRecycler?.addItemDecoration(getDivider())
        mAdapter = getAdapter()
        mAdapter?.openLoadAnimation(SLIDEIN_BOTTOM)
        mAdapter?.disableLoadMoreIfNotFullPage(mRecycler)
        mRecycler?.adapter = mAdapter

        mRefresh?.setOnRefreshListener { reload() }
        mAdapter?.setOnLoadMoreListener(this::loadNextPage, mRecycler)
        mAdapter?.setLoadMoreView(object : LoadMoreView() {
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

        })
        mRefresh?.isRefreshing = true
    }

    override fun requestData() {
        reload()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_recycler
    }

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return SmoothLinearLayoutManager(mRecycler?.context)
    }

    open fun getDivider(): RecyclerView.ItemDecoration {
        val divider = DividerItemDecoration(mRecycler?.context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.background_divider, null)!!)
        return divider
    }

    /**
     * 获取对应的Adapter
     */
    abstract fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder>

    /**
     * 加载服务器数据
     */
    abstract fun loadServer(isResetData: Boolean, nextCursor: Int = 0)


    fun setHasMore(hasMore: Boolean) {
        this.mHasMore = hasMore
    }

    fun setNextCursor(nextCursor: Int) {
        this.mNextCursor = nextCursor
    }

    private fun loadNextPage() {
        if (!mHasMore) {
            mAdapter?.loadMoreEnd()
            return
        }
        loadServer(false, mNextCursor)
        mIsShowNext = true
    }

    private fun reload() {
        this.mHasMore = false
        mIsShowNext = false
        mNextCursor = 0
        loadServer(true, 0)
    }

    fun updateList(lists: List<T>?, isReset: Boolean) {
        if (CollectionUtils.isNotEmpty(lists)) {
            if (isReset) {
                mFirstLoaded = true
                mAdapter?.setNewData(lists)
            } else {
                mAdapter?.addData(lists!!)
            }
        } else if (isReset) {
            mFirstLoaded = true
            mAdapter?.setNewData(lists)
        }
    }

    fun onComplete() {
        Log.d(Thread.currentThread().name, "class = RecyclerFragment rhjlog onComplete: ")
        if (mRefresh?.isRefreshing == true) {
            mRefresh?.isRefreshing = false
        }

        if (mIsShowNext) {
            if (mHasMore) {
                mAdapter?.loadMoreComplete()
            } else {
                mAdapter?.loadMoreEnd()
            }
            mIsShowNext = false
        }

    }

    fun onError(t: Throwable) {
        XLog.e("class = RecyclerFragment rhjlog onError: " + t.message)
        //todo 错误提示
        if (mRefresh?.isRefreshing == true) {
            mRefresh?.isRefreshing = false
        }

        if (mIsShowNext) {
            mAdapter?.loadMoreFail()
            mIsShowNext = false
        }

    }

    protected fun updateList(lists: List<T>?) {
        updateList(lists, false)
    }

    override fun onConfigFragment(bundle: Bundle) {}

    fun scrollToTop() {
//        mRecycler?.layoutManager?.scrollToPosition(0)
        mRecycler?.smoothScrollToPosition(0)
    }


}