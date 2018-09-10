package cn.imrhj.cowlevel.ui.fragment.element

import android.widget.ImageView
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.common.PostListCountApiModel
import cn.imrhj.cowlevel.network.model.element.GameModel
import cn.imrhj.cowlevel.ui.base.ApiRecyclerFragment
import cn.imrhj.cowlevel.utils.cdnImageForDPSize
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observable

class ElementGameFragment : ApiRecyclerFragment<GameModel, PostListCountApiModel<GameModel>>() {
    var mId = 0
    override fun showDivider(): Boolean {
        return false
    }

    override fun getApiObservable(nextCursor: Int): Observable<PostListCountApiModel<GameModel>> {
        return RetrofitManager.getInstance().elementGame(mId, nextCursor)
    }

    override fun onNext(result: PostListCountApiModel<GameModel>, isResetData: Boolean, nextCursor: Int) {
        updateList(result.list, isResetData)
        setNextCursor(nextCursor + 1)
        setHasMore(result.hasMore == 1)
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.item_game_row_small
    }

    override fun convert(helper: BaseViewHolder, item: GameModel) {
        val cover = helper.getView<ImageView>(R.id.iv_cover)
        Glide.with(this)
                .load(cdnImageForDPSize(item.pic, 120, 60))
                .into(cover)
        val price = if (item.gamePrices?.size ?: 0 > 0) item.gamePrices?.get(0)?.data else null
        helper.setText(R.id.tv_title, item.chineseTitle)
                .setText(R.id.tv_subtitle, item.gamePublishDateShow)
                .setText(R.id.tv_score, item.starAvg)
                .setText(R.id.tv_score_number, "(${item.playedCount})")
                .setText(R.id.tv_platform, item.platformSupportList?.map { it?.name }?.reduce { acc, s -> "$acc / $s" })
//                .setText(R.id.tv_price, )

    }
}