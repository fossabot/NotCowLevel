package cn.imrhj.cowlevel.ui.fragment

import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.ui.adapter.FeedAdapter
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers

class ElementFeedFragment : RecyclerFragment<BaseModel>() {
    var mId: Int = -1

//    fun setData()

    override fun getAdapter(): BaseQuickAdapter<BaseModel, BaseViewHolder> {
        return object : FeedAdapter(ArrayList(), this) {
            override fun convert(helper: BaseViewHolder?, item: BaseModel?) {
//                if (helper?.itemViewType == )
                super.convert(helper, item)
            }
        }
    }

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        RetrofitManager.getInstance().getElementFeed(mId, nextCursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    updateList(result.list, isResetData)
                    setHasMore(result.has_more == 1)
                    setNextCursor(nextCursor + 1)
                }, mOnError, mOnComplete)
    }

}