package cn.imrhj.cowlevel.ui.base

import android.support.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

abstract class ApiRecyclerFragment<T, S> : RecyclerFragment<T>() {
    abstract fun getApiObservable(nextCursor: Int): Observable<S>
    abstract fun onNext(result: S, isResetData: Boolean, nextCursor: Int)
    @LayoutRes
    abstract fun getItemLayoutResId(): Int

    abstract fun convert(helper: BaseViewHolder, item: T)

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        getApiObservable(nextCursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver<S> { this.onNext(it, isResetData, nextCursor) })
    }

    override fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder> {
        return object : BaseQuickAdapter<T, BaseViewHolder>(this.getItemLayoutResId()) {
            override fun convert(helper: BaseViewHolder?, item: T?) {
                if (helper != null && item != null) {
                    this@ApiRecyclerFragment.convert(helper, item)
                }
            }
        }
    }

    /**
     * 默认页面 index 从1开始
     */
    override fun getFirstPageIndex(): Int {
        return 1
    }
}