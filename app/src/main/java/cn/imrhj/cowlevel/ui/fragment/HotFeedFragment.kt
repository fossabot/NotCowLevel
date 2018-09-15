package cn.imrhj.cowlevel.ui.fragment

import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.ui.adapter.FeedAdapter
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by rhj on 10/12/2017.
 */
class HotFeedFragment : RecyclerFragment<BaseModel>() {
    override fun getAdapter(): BaseQuickAdapter<BaseModel, BaseViewHolder> {
        return FeedAdapter(ArrayList(), this)
    }

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        RetrofitManager.getInstance().hotFeeds(nextCursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver {
                    updateList(it.list, isResetData)
                    setHasMore(it.has_more == 1)
                    setNextCursor(nextCursor + 1)
                })
    }
}