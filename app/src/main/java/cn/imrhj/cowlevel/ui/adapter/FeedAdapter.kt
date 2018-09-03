package cn.imrhj.cowlevel.ui.adapter

import android.support.v4.app.Fragment
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.FeedModel
import cn.imrhj.cowlevel.network.model.TYPE_FEED
import cn.imrhj.cowlevel.network.model.list.FollowedPostNewListModel
import cn.imrhj.cowlevel.network.model.list.FollowedTagNewListModel
import cn.imrhj.cowlevel.network.model.list.TYPE_HEADER_POST
import cn.imrhj.cowlevel.network.model.list.TYPE_HEADER_TAG
import cn.imrhj.cowlevel.ui.adapter.holder.FeedHolder
import cn.imrhj.cowlevel.ui.adapter.holder.HomeHeaderHolder
import cn.imrhj.cowlevel.utils.ScreenSizeUtil.dp2px
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate

/**
 * Created by rhj on 2017/12/9.
 */
val DP130_2PX = dp2px(130)
val DP65_2PX = dp2px(65)

open class FeedAdapter(data: MutableList<BaseModel>?, fragment: Fragment) : BaseQuickAdapter<BaseModel, BaseViewHolder>(data) {
    private val feedHolder = FeedHolder(fragment)
    private val homeHeaderHolder = HomeHeaderHolder(fragment)

    init {
        multiTypeDelegate = object : MultiTypeDelegate<BaseModel>() {
            override fun getItemType(t: BaseModel?): Int {
                if (t != null) {
                    return t.getType()
                }
                return 0
            }
        }
        multiTypeDelegate.registerItemType(TYPE_FEED, R.layout.item_feed_common)
        multiTypeDelegate.registerItemType(TYPE_HEADER_POST, R.layout.item_home_header)
        multiTypeDelegate.registerItemType(TYPE_HEADER_TAG, R.layout.item_home_header)
    }

    override fun convert(helper: BaseViewHolder?, item: BaseModel?) {
        when (helper?.itemViewType) {
            TYPE_FEED -> feedHolder.renderCommon(helper, item as FeedModel)
            TYPE_HEADER_POST -> homeHeaderHolder.renderPost(helper, (item as FollowedPostNewListModel).list)
            TYPE_HEADER_TAG -> homeHeaderHolder.renderTag(helper, (item as FollowedTagNewListModel).list)
        }
    }
}
