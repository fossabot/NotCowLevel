package cn.imrhj.cowlevel.ui.fragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.FeedModel
import cn.imrhj.cowlevel.network.model.FeedModel.Type.*
import cn.imrhj.cowlevel.ui.base.LazyLoadFragment
import cn.imrhj.cowlevel.utils.cdnImageForSize
import cn.imrhj.cowlevel.utils.cdnImageForSquare
import cn.imrhj.cowlevel.utils.dp2px
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.elvishew.xlog.XLog
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_feed.*

/**
 * Created by rhj on 2017/11/28.
 */
class FeedFragment : LazyLoadFragment() {
    private var mNextCursor = 0
    private val mData = ArrayList<FeedModel>()

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.background_divider, null)!!)
        recycler.addItemDecoration(divider)
        recycler.adapter = FeedAdapter(mData)

    }

    override fun layoutId(): Int {
        return R.layout.fragment_feed
    }

    override fun onConfigFragment(bundle: Bundle) {
    }

    override fun requestData() {
        RetrofitManager.getInstance().request().feedTimeline()
                .map { it.data }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    mNextCursor = result.last_id
                    mData.addAll(result.list!!)
                    recycler.adapter.notifyDataSetChanged()
                })


    }

    class FeedAdapter(data: MutableList<FeedModel>?) : BaseQuickAdapter<FeedModel, BaseViewHolder>(data) {
        init {
            multiTypeDelegate = object : MultiTypeDelegate<FeedModel>() {
                override fun getItemType(t: FeedModel): Int {
                    var value = type_unknow.ordinal
                    try {
                        value = valueOf(t.action).ordinal
                    } catch (e: IllegalArgumentException) {
                        XLog.b().e("type = ${t.action}", e)
                    }
                    return value
                }
            }
            multiTypeDelegate.registerItemType(type_unknow.ordinal, R.layout.item_test)
                    .registerItemType(tag_question.ordinal, R.layout.item_feed_tag_question)
                    .registerItemType(tag_article.ordinal, R.layout.item_feed_editor_elite_article)
                    .registerItemType(vote_comment.ordinal, R.layout.item_test)
                    .registerItemType(sharelink_comment.ordinal, R.layout.item_test)
                    .registerItemType(follow_question.ordinal, R.layout.item_test)
                    .registerItemType(post_submit_answer.ordinal, R.layout.item_test)
                    .registerItemType(editor_elite_answer.ordinal, R.layout.item_feed_tag_answer)
                    .registerItemType(editor_elite_review.ordinal, R.layout.item_test)
                    .registerItemType(system_recommend_user.ordinal, R.layout.item_test)
                    .registerItemType(editor_elite_article.ordinal, R.layout.item_feed_editor_elite_article)
                    .registerItemType(post_submit_review.ordinal, R.layout.item_test)
                    .registerItemType(post_submit_question.ordinal, R.layout.item_test)
                    .registerItemType(tag_answer.ordinal, R.layout.item_feed_tag_answer)
                    .registerItemType(vote_answer.ordinal, R.layout.item_test)
        }

        override fun convert(helper: BaseViewHolder?, item: FeedModel?) {
            when (helper?.itemViewType) {
                tag_answer.ordinal -> renderTagAnswer(helper, item)
                tag_question.ordinal -> renderTagQuestion(helper, item)
                tag_article.ordinal -> renderEditorEliteArticle(helper, item)
                editor_elite_answer.ordinal -> renderTagAnswer(helper, item)
                editor_elite_article.ordinal -> renderEditorEliteArticle(helper, item)
            }
        }

        private fun renderTagAnswer(helper: BaseViewHolder?, item: FeedModel?) {
            renderHeader(helper, item)
            renderUser(helper, item)
            val question = item?.question
            val answer = item?.answer
            helper?.setText(R.id.title, question?.title)
            helper?.setText(R.id.content, answer?.neat_content?.desc)
            val thumb = helper?.getView<ImageView>(R.id.thumb)
            if (answer?.neat_content?.thumb != null && answer.neat_content.thumb.isNotBlank()) {
                thumb?.visibility = View.VISIBLE
                Picasso.with(App.getApplication().applicationContext)
                        .load(cdnImageForSquare(answer.neat_content.thumb, dp2px(100F)))
                        .into(thumb)
            } else {
                thumb?.visibility = View.GONE
            }
//            renderNavBar(helper, item, null, 0, question?.follower_count,
//                    question?.is_follow!!, question.answer_count)
        }

        private fun renderTagQuestion(helper: BaseViewHolder?, item: FeedModel?) {
            renderHeader(helper, item)
            val question = item?.question
            helper?.setText(R.id.title, question?.title)

            renderNavBar(helper, item, null, 0, question?.follower_count,
                    question?.is_follow!!, question.answer_count)
        }

        private fun renderEditorEliteArticle(helper: BaseViewHolder?, item: FeedModel?) {
            renderHeader(helper, item)
            renderUser(helper, item)
            val article = item?.article
            val pic = helper?.getView<ImageView>(R.id.pic)
            helper?.setText(R.id.title, article?.title)
            helper?.setText(R.id.content, article?.brief_content?.desc)
            val thumb = helper?.getView<ImageView>(R.id.thumb)
            if (article?.brief_content?.thumb != null && article.brief_content.thumb.isNotBlank()) {
                thumb?.visibility = View.VISIBLE
                Picasso.with(App.getApplication().applicationContext)
                        .load(cdnImageForSquare(article.brief_content.thumb, dp2px(100F)))
                        .into(thumb)
            } else {
                thumb?.visibility = View.GONE
            }
            if (article?.pic != null && article.pic.isNotBlank()) {
                pic?.visibility = View.VISIBLE
                pic?.post {
                    Picasso.with(App.getApplication().applicationContext)
                            .load(cdnImageForSize(article.pic, pic.width, pic.height))
                            .into(pic)
                }
            } else {
                pic?.visibility = View.GONE
            }
        }

        /**
         * 渲染顶部的tag
         */
        private fun renderHeader(helper: BaseViewHolder?, item: FeedModel?) {
            val tags = item?.merged?.tags
            val header = helper?.getView<View>(R.id.header)
            if (tags != null && tags.isNotEmpty()) {
                header?.visibility = View.VISIBLE
                val tag = helper?.getView<TextView>(R.id.tag)
                tag?.text = tags[0].name
                helper?.setText(R.id.time, item.publish_time_before)
            } else {
                header?.visibility = View.GONE

            }
        }

        /**
         * 渲染底部NavBar
         */
        private fun renderNavBar(helper: BaseViewHolder?, item: FeedModel?,
                                 voterCount: Int?, hasVote: Int = 0,
                                 followerCount: Int?, hasFollow: Int = 0,
                                 commentCount: Int = 0, commentBtnTitle: String = "评论") {
            val voteView = helper?.getView<LinearLayout>(R.id.btn_vote)
            voteView?.visibility = if (voterCount == null) View.GONE else View.VISIBLE
            //todo 添加赞同按钮事件
            if (voterCount != null) {
                helper?.setText(R.id.tv_vote, "${if (hasVote == 0) "赞同" else "已赞"} $voterCount")
            }

            val followView = helper?.getView<LinearLayout>(R.id.btn_follower)
            followView?.visibility = if (followerCount == null) View.GONE else View.VISIBLE
            if (followerCount != null) {
                helper?.setText(R.id.tv_follow, if (hasFollow == 0) "关注" else "已关注")
            }

            helper?.setText(R.id.tv_commit, "$commentBtnTitle $commentCount")
        }

        /**
         * 渲染用户信息
         */
        private fun renderUser(helper: BaseViewHolder?, item: FeedModel?) {
            val user = item?.user
            Picasso.with(App.getApplication().applicationContext)
                    .load(cdnImageForSize(user?.avatar, dp2px(48F)))
                    .into(helper?.getView<CircleImageView>(R.id.avatar))
            helper?.setText(R.id.name, user?.name)
            helper?.setText(R.id.intro, user?.intro)
            helper?.setText(R.id.subtitle, item?.action_text)
        }

    }
}