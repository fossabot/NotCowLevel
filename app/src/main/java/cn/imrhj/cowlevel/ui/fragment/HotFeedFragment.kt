package cn.imrhj.cowlevel.ui.fragment

import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.FeedModel
import cn.imrhj.cowlevel.ui.adapter.FeedAdapter
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by rhj on 10/12/2017.
 */
class HotFeedFragment : RecyclerFragment<FeedModel>() {
    override fun getAdapter(): BaseQuickAdapter<FeedModel, BaseViewHolder> {
        return FeedAdapter(ArrayList())
    }

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        RetrofitManager.getInstance().request().hotFeeds(nextCursor)
                .map { it.data }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    updateList(result.list, isResetData)
                    setHasMore(result.has_more == 1)
                    setNextCursor(nextCursor + 1)
                }, mOnError, mOnComplete)
    }
}