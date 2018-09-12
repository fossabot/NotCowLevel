package cn.imrhj.cowlevel.ui.fragment.element

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.common.ListCountApiModel
import cn.imrhj.cowlevel.network.model.element.QuestionModel
import cn.imrhj.cowlevel.ui.base.ApiRecyclerFragment
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observable

class ElementQuestionFragment : ApiRecyclerFragment<QuestionModel, ListCountApiModel<QuestionModel>>() {
    var mId: Int = -1
    override fun getApiObservable(nextCursor: Int): Observable<ListCountApiModel<QuestionModel>> {
        return RetrofitManager.getInstance().elementQuestion(mId, nextCursor)
    }

    override fun onNext(result: ListCountApiModel<QuestionModel>, isResetData: Boolean, nextCursor: Int) {
        updateList(result.list, isResetData)
        setHasMore(result.hasMore == 1)
        setNextCursor(nextCursor + 1)
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.item_question
    }

    override fun convert(helper: BaseViewHolder, item: QuestionModel) {
        helper.setText(R.id.title, item.title)
        helper.setText(R.id.content, item.content)
        helper.setGone(R.id.btn_vote, false)
        helper.setText(R.id.tv_commit, "回答 ${item.answerCount}")
    }
}