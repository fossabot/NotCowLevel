package cn.imrhj.cowlevel.ui.adapter.holder

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.manager.SchemeUtils
import cn.imrhj.cowlevel.network.manager.COW_LEVEL_URL
import cn.imrhj.cowlevel.network.model.UserModel
import cn.imrhj.cowlevel.utils.StringUtils
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by rhj on 22/12/2017.
 */
class UserHolder {
    fun renderHeader(helper: BaseViewHolder, userModel: UserModel) {
        helper.setText(R.id.tv_name, userModel.name)
        setOrHideText(helper.getView(R.id.tv_intro), userModel.intro)
        helper.setText(R.id.tv_vote, "获得 ${userModel.vote_count} 赞")
        helper.setText(R.id.tv_follower, "关注者 ${userModel.follower_count}")
        setOrHideText(helper.getView(R.id.tv_bio), userModel.user_bio)
        helper.setText(R.id.tv_played, userModel.played_count.toString())
        helper.setText(R.id.tv_comment, userModel.comment_count.toString())
        helper.setText(R.id.tv_answer, userModel.answer_count.toString())
        helper.setText(R.id.tv_question, userModel.question_count.toString())
        helper.setText(R.id.tv_article, userModel.article_count.toString())
        helper.setText(R.id.tv_collection, userModel.collection_count.toString())

        helper.getView<View>(R.id.ll_msg).setOnClickListener {
            SchemeUtils.openLink(COW_LEVEL_URL + "inbox/${userModel.url_slug}")
        }

    }

    private fun setOrHideText(view: TextView, text: String?) {
        if (StringUtils.isBlank(text)) {
            view.visibility = View.GONE
        } else {
            view.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(text, 0) else Html.fromHtml(text)
        }
    }
}