package cn.imrhj.cowlevel.ui.fragment.element

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.common.PostListCountApiModel
import cn.imrhj.cowlevel.network.model.element.GameModel
import cn.imrhj.cowlevel.ui.base.ApiRecyclerFragment
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observable

class ElementGameFragment : ApiRecyclerFragment<GameModel, PostListCountApiModel<GameModel>>() {
    var mId = 0
    override fun getApiObservable(nextCursor: Int): Observable<PostListCountApiModel<GameModel>> {
        return RetrofitManager.getInstance().elementGame(mId, nextCursor)
    }

    override fun onNext(result: PostListCountApiModel<GameModel>, isResetData: Boolean, nextCursor: Int) {
        updateList(result.list, isResetData)
        setHasMore(result.hasMore == 1)
        setNextCursor(nextCursor + 1)
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.item_game_row_small
    }

    override fun convert(helper: BaseViewHolder, item: GameModel) {
    }
}