package cn.imrhj.cowlevel.ui.adapter.provider.game

import android.widget.LinearLayout
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.common.UrlListModel
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider

class GameStoreProvider : BaseItemProvider<UrlListModel, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_game_store
    }

    override fun viewType(): Int {
        return ItemTypeEnum.TYPE_URLS.ordinal
    }

    override fun convert(helper: BaseViewHolder, data: UrlListModel, position: Int) {
        helper.setText(R.id.tv_title, "购买与下载")
        val parent = helper.getView<LinearLayout>(R.id.ll_store)

    }
}