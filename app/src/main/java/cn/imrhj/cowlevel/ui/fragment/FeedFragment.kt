package cn.imrhj.cowlevel.ui.fragment

import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.FeedModel.Type.system_recommend_user
import cn.imrhj.cowlevel.ui.adapter.FeedAdapter
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * feed详情
 * Created by rhj on 2017/11/28.
 */
class FeedFragment : RecyclerFragment<BaseModel>() {
    private var mFirstId = 0

    override fun getAdapter(): BaseQuickAdapter<BaseModel, BaseViewHolder> {
        return FeedAdapter(ArrayList(), this)
    }

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        RetrofitManager.getInstance().feedTimeline(nextCursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if (result.first_id == mFirstId && !isResetData) {
                        return@subscribe
                    }
                    mFirstId = result.first_id
                    updateList(result.list?.filter { it.action != system_recommend_user.name }, isResetData)
                    setHasMore(result.has_more == 1)
                    setNextCursor(result.last_id)
                }, mOnError, mOnComplete)
    }

}