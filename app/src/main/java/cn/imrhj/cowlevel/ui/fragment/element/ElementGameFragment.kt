package cn.imrhj.cowlevel.ui.fragment.element

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.common.ListCountApiModel
import cn.imrhj.cowlevel.network.model.element.GameModel
import cn.imrhj.cowlevel.ui.base.ApiRecyclerFragment
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observable

class ElementGameFragment : ApiRecyclerFragment<GameModel, ListCountApiModel<GameModel>>() {
    var mId = 0
    override fun getApiObservable(nextCursor: Int): Observable<ListCountApiModel<GameModel>> {
        return RetrofitManager.getInstance().elementGame(mId, nextCursor)
    }

    override fun onNext(result: ListCountApiModel<GameModel>, isResetData: Boolean, nextCursor: Int) {
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.item_game_layout
    }

    override fun convert(helper: BaseViewHolder, item: GameModel) {
    }
}