package cn.imrhj.cowlevel.ui.adapter.provider.game

import android.widget.LinearLayout
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.extensions.reduceNullable
import cn.imrhj.cowlevel.network.model.game.GameModel
import cn.imrhj.cowlevel.ui.view.SimpleGameTagView
import cn.imrhj.cowlevel.utils.ScreenSizeUtil.dp2px
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider

class GameHeaderProvider : BaseItemProvider<GameModel, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_game_header
    }

    override fun viewType(): Int {
        return ItemTypeEnum.TYPE_GAME_HEADER.ordinal
    }

    override fun convert(helper: BaseViewHolder, data: GameModel, position: Int) {
        helper.setText(R.id.tv_title, data.title)
                .setText(R.id.tv_score_number, data.totalStarAvg)
                .setText(R.id.tv_time, "${data.playTimeAvg ?: 0}h")
                .setText(R.id.tv_want_play, "${data.wishCount} 人想玩")
                .setText(R.id.tv_played, "${data.playedCount} 人玩过")
                .setText(R.id.tv_release_date, "${data.gamePublishDateShow}")
                .setText(R.id.tv_platform, data.platformSupportList?.map { it?.name }
                        ?.reduceNullable { acc, s -> "$acc / $s" })
                .setText(R.id.tv_language, data.language?.map { it?.name }
                        ?.reduceNullable { acc, s -> "$acc / $s" })
                .setText(R.id.tv_number_network, data.playType?.map { it?.name }
                        ?.reduceNullable { acc, s -> "$acc / $s" })
                .setText(R.id.tv_desc, data.summary)

        val tagParent = helper.getView<LinearLayout>(R.id.ll_tags)
        data.tags?.forEach {
            if (it != null) {
                val tagView = SimpleGameTagView(tagParent.context, it)
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.marginEnd = dp2px(8)
                tagParent.addView(tagView, params)
            }
        }
    }
}