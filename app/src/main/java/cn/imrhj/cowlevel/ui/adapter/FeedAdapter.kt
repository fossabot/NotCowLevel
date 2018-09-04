package cn.imrhj.cowlevel.ui.adapter

import android.support.v4.app.Fragment
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_FEED
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.feed.FeedModel
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

open class FeedAdapter(data: MutableList<BaseModel>?, fragment: Fragment) : BaseQuickAdapter<BaseModel, BaseViewHolder>(data) {
    private val feedHolder = FeedHolder(fragment)

    init {
        multiTypeDelegate = object : MultiTypeDelegate<BaseModel>() {
            override fun getItemType(t: BaseModel?): Int {
                if (t != null) {
                    return t.getType().ordinal
                }
                return 0
            }
        }
        this.initType(multiTypeDelegate)
    }

    open fun initType(multiTypeDelegate: MultiTypeDelegate<BaseModel>) {
        multiTypeDelegate.registerItemType(TYPE_FEED.ordinal, R.layout.item_feed_common)

    }

    override fun convert(helper: BaseViewHolder?, item: BaseModel?) {
        if (helper != null) {
            val type = ItemTypeEnum.valueOf(helper.itemViewType)

            when (type) {
                TYPE_FEED -> feedHolder.renderCommon(helper, item as FeedModel)
                else -> {
                    this.extendConvert(helper, item, type)
                }
            }
        }
    }

    open fun extendConvert(helper: BaseViewHolder, item: BaseModel?, type: ItemTypeEnum) {
    }
}
