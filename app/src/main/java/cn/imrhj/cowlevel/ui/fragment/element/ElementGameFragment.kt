package cn.imrhj.cowlevel.ui.fragment.element

import android.widget.ImageView
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.extensions.getLastOrEmpty
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.common.PostListCountApiModel
import cn.imrhj.cowlevel.network.model.element.SimpleGameModel
import cn.imrhj.cowlevel.ui.base.ApiRecyclerFragment
import cn.imrhj.cowlevel.utils.cdnImageForDPSize
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observable

class ElementGameFragment : ApiRecyclerFragment<SimpleGameModel, PostListCountApiModel<SimpleGameModel>>() {
    var mId = 0
    override fun showDivider(): Boolean {
        return false
    }

//    override fun layoutId(): Int {
//        return R.layout.fragment_recycler_ftb
//    }

    override fun getApiObservable(nextCursor: Int): Observable<PostListCountApiModel<SimpleGameModel>> {
        return RetrofitManager.getInstance().elementGame(mId, nextCursor)
    }

    override fun onNext(result: PostListCountApiModel<SimpleGameModel>, isResetData: Boolean, nextCursor: Int) {
        updateList(result.list, isResetData)
        setNextCursor(nextCursor + 1)
        setHasMore(result.hasMore == 1)
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.item_game_row_small
    }

    override fun convert(helper: BaseViewHolder, item: SimpleGameModel) {
        val cover = helper.getView<ImageView>(R.id.iv_cover)
        Glide.with(this)
                .load(cdnImageForDPSize(item.pic, 120, 60))
                .into(cover)
        val price = item.gamePrices?.sortedBy { it?.data?.cnyPrice?.toDouble() }?.take(1)?.getLastOrEmpty()
        helper.setText(R.id.tv_title, item.chineseTitle)
                .setText(R.id.tv_subtitle, item.gamePublishDateShow)
                .setText(R.id.tv_score, item.starAvg)
                .setText(R.id.tv_score_number, "(${item.playedCount})")
                .setText(R.id.tv_platform,
                        if (item.platformSupportList?.size ?: 0 > 0)
                            item.platformSupportList?.map { it?.name }?.reduce { acc, s -> "$acc / $s" }
                        else "")
                .setText(R.id.tv_price, "¥${price?.data?.cnyPrice}")
                .setText(R.id.tv_off, price?.data?.priceOff)
                .setGone(R.id.tv_price, price?.data?.cnyPrice != null)
                .setGone(R.id.tv_off, price?.data?.priceOff?.length ?: 0 > 0)
    }
}