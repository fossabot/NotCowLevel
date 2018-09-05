package cn.imrhj.cowlevel.ui.fragment.element

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.element.QuestionModel
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers

class ElementQuestionFragment : RecyclerFragment<QuestionModel>() {
    var mId: Int = -1
    override fun getAdapter(): BaseQuickAdapter<QuestionModel, BaseViewHolder> {
        return object : BaseQuickAdapter<QuestionModel, BaseViewHolder>(R.layout.item_question) {
            override fun convert(helper: BaseViewHolder?, item: QuestionModel?) {
                helper?.setText(R.id.title, item?.title)
                helper?.setText(R.id.content, item?.content)
                helper?.setGone(R.id.btn_vote, false)
                helper?.setText(R.id.tv_commit, "回答 ${item?.answerCount}")
            }
        }
    }

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        RetrofitManager.getInstance().elementQuestion(mId, nextCursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    updateList(result.list, isResetData)
                    setHasMore(result.hasMore == 1)
                    setNextCursor(nextCursor + 1)
                }, mOnError, mOnComplete)
    }

    override fun getFirstPageIndex(): Int {
        return 1
    }
}