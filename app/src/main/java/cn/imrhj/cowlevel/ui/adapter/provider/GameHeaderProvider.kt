package cn.imrhj.cowlevel.ui.adapter.provider

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.game.GameModel
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
                .setText(R.id.tv_platform, data.platformSupportList?.asSequence()?.map { it?.name }
                        ?.reduce { acc, s -> "$acc / $s" })
                .setText(R.id.tv_language, data.language?.asSequence()?.map { it?.name }
                        ?.reduce { acc, s -> "$acc / $s" })
                .setText(R.id.tv_number_network, data.playType?.asSequence()?.map { it?.name }
                        ?.reduce { acc, s -> "$acc / $s" })
                .setText(R.id.tv_desc, data.summary)
    }
}