package cn.imrhj.cowlevel.ui.adapter

import android.app.Fragment
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.FeedModel
import cn.imrhj.cowlevel.network.model.TYPE_FEED
import cn.imrhj.cowlevel.ui.adapter.holder.FeedHolder
import cn.imrhj.cowlevel.utils.ScreenSizeUtil.dp2px
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate

/**
 * Created by rhj on 2017/12/9.
 */
val DP130_2PX = dp2px(130)
val DP65_2PX = dp2px(65)

class FeedAdapter(data: MutableList<BaseModel>?, fragment: Fragment) : BaseQuickAdapter<BaseModel, BaseViewHolder>(data) {
    private val feedHolder = FeedHolder(fragment)

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
    }

    override fun convert(helper: BaseViewHolder?, item: BaseModel?) {
        when (helper?.itemViewType) {
            1 -> feedHolder.renderCommon(helper, item as FeedModel)
        }
    }
}
