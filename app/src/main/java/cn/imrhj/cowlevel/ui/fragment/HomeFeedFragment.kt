package cn.imrhj.cowlevel.ui.fragment

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_HEADER_POST
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_HEADER_TAG
import cn.imrhj.cowlevel.network.manager.HtmlParseManager
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.feed.FeedApiModel
import cn.imrhj.cowlevel.network.model.feed.FeedModel.Type.system_recommend_user
import cn.imrhj.cowlevel.network.model.home.FeedHomeModel
import cn.imrhj.cowlevel.network.model.list.FollowedPostNewListModel
import cn.imrhj.cowlevel.network.model.list.FollowedTagNewListModel
import cn.imrhj.cowlevel.ui.adapter.FeedAdapter
import cn.imrhj.cowlevel.ui.adapter.holder.HomeHeaderHolder
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * feed详情
 * Created by rhj on 2017/11/28.
 */
class HomeFeedFragment : RecyclerFragment<BaseModel>() {
    private var mFirstId = 0

    override fun getAdapter(): BaseQuickAdapter<BaseModel, BaseViewHolder> {
        return object : FeedAdapter(ArrayList(), this) {
            private val homeHeaderHolder = HomeHeaderHolder(this@HomeFeedFragment)
            override fun initType(multiTypeDelegate: MultiTypeDelegate<BaseModel>) {
                super.initType(multiTypeDelegate)
                multiTypeDelegate.registerItemType(TYPE_HEADER_POST.ordinal, R.layout.item_home_header)
                multiTypeDelegate.registerItemType(TYPE_HEADER_TAG.ordinal, R.layout.item_home_header)
            }

            override fun extendConvert(helper: BaseViewHolder, item: BaseModel?, type: ItemTypeEnum) {
                when (type) {
                    TYPE_HEADER_POST -> homeHeaderHolder.renderPost(helper, (item as FollowedPostNewListModel).list)
                    TYPE_HEADER_TAG -> homeHeaderHolder.renderTag(helper, (item as FollowedTagNewListModel).list)
                    else -> {
                    }
                }
            }
        }
    }

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        if (isResetData) {
            HtmlParseManager.getHomeHtml()
                    .flatMap {
                        if (it.feedData == null) {
                            RetrofitManager.getInstance().feedTimeline(nextCursor)
                        } else {
                            Observable.just(it)
                        }
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it is FeedApiModel) {
                            this.onLoadEnd(it, isResetData)
                        } else if (it is FeedHomeModel) {
                            val list: MutableList<BaseModel> = ArrayList()
                            if (it.followedPostNews != null && it.followedPostNews?.list != null) {
                                list.add(it.followedPostNews!!)
                            }
                            if (it.followedTagNews != null && it.followedTagNews?.list != null) {
                                list.add(it.followedTagNews!!)
                            }
                            list.addAll(it.feedData?.list?.filter { it.action != system_recommend_user.name }!!)
                            mFirstId = it.feedData?.first_id ?: mFirstId
                            updateList(list, isResetData)
                            setHasMore(it.feedData?.has_more == 1)
                            setNextCursor(it.feedData?.last_id!!)
                        }
                    }, mOnError, mOnComplete)
        } else {
            RetrofitManager.getInstance().feedTimeline(nextCursor)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        if (result.first_id == mFirstId && !isResetData) {
                            return@subscribe
                        }
                        this.onLoadEnd(result, isResetData)
                    }, mOnError, mOnComplete)
        }
    }

    private fun onLoadEnd(data: FeedApiModel, isResetData: Boolean) {
        mFirstId = data.first_id
        updateList(data.list?.filter { it.action != system_recommend_user.name }, isResetData)
        setHasMore(data.has_more == 1)
        setNextCursor(data.last_id)
    }
}