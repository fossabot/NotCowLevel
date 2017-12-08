package cn.imrhj.cowlevel.ui.fragment

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.extensions.setTextAndShow
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.FeedModel
import cn.imrhj.cowlevel.network.model.FeedModel.Type.*
import cn.imrhj.cowlevel.ui.base.LazyLoadFragment
import cn.imrhj.cowlevel.utils.StringUtil
import cn.imrhj.cowlevel.utils.cdnImageForSize
import cn.imrhj.cowlevel.utils.cdnImageForSquare
import cn.imrhj.cowlevel.utils.dp2px
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
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
                    mData.addAll(result.list?.filter { it.action != system_recommend_user.name } ?: ArrayList())
                    recycler.adapter.notifyDataSetChanged()
                })


    }

    class FeedAdapter(data: MutableList<FeedModel>?) : BaseQuickAdapter<FeedModel, BaseViewHolder>(data) {
        init {
            multiTypeDelegate = object : MultiTypeDelegate<FeedModel>() {
                override fun getItemType(t: FeedModel): Int {
                    return 0
                }
            }
            multiTypeDelegate.registerItemTypeAutoIncrease(R.layout.item_feed_common)
//            multiTypeDelegate.registerItemType(type_unknow.ordinal, R.layout.item_test)
//                    .registerItemType(tag_question.ordinal, R.layout.item_feed_tag_question)
//                    .registerItemType(tag_article.ordinal, R.layout.item_feed_editor_elite_article)
//                    .registerItemType(vote_comment.ordinal, R.layout.item_test)
//                    .registerItemType(sharelink_comment.ordinal, R.layout.item_test)
//                    .registerItemType(follow_question.ordinal, R.layout.item_test)
//                    .registerItemType(post_submit_answer.ordinal, R.layout.item_test)
//                    .registerItemType(editor_elite_answer.ordinal, R.layout.item_feed_tag_answer)
//                    .registerItemType(editor_elite_review.ordinal, R.layout.item_test)
//                    .registerItemType(system_recommend_user.ordinal, R.layout.item_test)
//                    .registerItemType(editor_elite_article.ordinal, R.layout.item_feed_editor_elite_article)
//                    .registerItemType(post_submit_review.ordinal, R.layout.item_test)
//                    .registerItemType(post_submit_question.ordinal, R.layout.item_test)
//                    .registerItemType(tag_answer.ordinal, R.layout.item_feed_tag_answer)
//                    .registerItemType(vote_answer.ordinal, R.layout.item_test)
        }

        override fun convert(helper: BaseViewHolder?, item: FeedModel?) {
            when (helper?.itemViewType) {
                1 -> {
                }
                else -> renderCommon(helper, item)
            }
        }

        private fun renderCommon(helper: BaseViewHolder?, item: FeedModel?) {
            renderHeader(helper, item)
            renderUser(helper, item)
            val vPic = helper?.getView<ImageView>(R.id.pic)
            val vTitle = helper?.getView<TextView>(R.id.title)
            val vContent = helper?.getView<TextView>(R.id.content)
            val vThumb = helper?.getView<ImageView>(R.id.thumb)
            vPic?.visibility = GONE
            vTitle?.visibility = GONE
            vContent?.visibility = GONE
            vThumb?.visibility = GONE
            if (item != null) {
                when (item.action) {
                    tag_answer.name -> this.renderTagAnswer(helper, item)
                    editor_elite_article.name -> this.renderEditorEliteArticle(helper, item)
                    editor_elite_answer.name -> this.renderTagAnswer(helper, item)
                    editor_elite_review.name -> this.renderEditorEliteReview(helper, item)

                }

            }
        }

        private fun renderTagAnswer(helper: BaseViewHolder?, item: FeedModel?) {
            val answer = item?.answer
            val question = item?.question
            renderTitle(helper, question?.title)
            renderContent(helper, answer?.neat_content?.desc)
            renderThumb(helper, answer?.neat_content?.thumb)
            renderNavBar(helper, item, answer?.vote_count, answer?.has_vote, question?.is_follow,
                    question?.comment_count)
        }

        private fun renderTagQuestion(helper: BaseViewHolder?, item: FeedModel?) {
            val question = item?.question
            helper?.setText(R.id.title, question?.title)

            renderNavBar(helper, item, null, 0, question?.is_follow!!, question.answer_count)
        }

        private fun renderEditorEliteArticle(helper: BaseViewHolder?, item: FeedModel?) {
            val article = item?.article
            renderTitle(helper, article?.title)
            renderContent(helper, article?.neat_content?.desc)
            renderThumb(helper, article?.neat_content?.thumb)
            renderPic(helper, article?.pic)
            renderNavBar(helper, item, article?.vote_count, article?.has_vote, null,
                    article?.comment_count)
        }

        private fun renderEditorEliteReview(helper: BaseViewHolder?, item: FeedModel?) {
            val review = item?.review
            renderContent(helper, review?.neat_content?.desc)
            renderThumb(helper, review?.neat_content?.thumb)
            renderNavBar(helper, item, review?.vote_count, review?.has_vote, null,
                    review?.comment_count)
        }

        private fun renderTitle(helper: BaseViewHolder?, title: String?) {
            helper?.getView<TextView>(R.id.title)?.setTextAndShow(title)
        }

        private fun renderContent(helper: BaseViewHolder?, content: String?) {
            helper?.getView<TextView>(R.id.content)?.setTextAndShow(content)
        }

        private fun renderThumb(helper: BaseViewHolder?, thumb: String?) {
            if (StringUtil.isNotBlank(thumb)) {
                val view = helper?.getView<ImageView>(R.id.thumb)
                view?.visibility = VISIBLE
                Picasso.with(App.getApplication().applicationContext)
                        .load(cdnImageForSquare(thumb, dp2px(100F)))
                        .into(view)
            }
        }

        private fun renderPic(helper: BaseViewHolder?, pic: String?) {
            if (StringUtil.isNotBlank(pic)) {
                val imageView = helper?.getView<ImageView>(R.id.pic)
                imageView?.visibility = VISIBLE
                imageView?.post {
                    Picasso.with(App.getAppContext())
                            .load(cdnImageForSize(pic, imageView.width, imageView.height))
                            .into(imageView)
                }
            }
        }

        /**
         * 渲染顶部的tag
         */
        private fun renderHeader(helper: BaseViewHolder?, item: FeedModel?) {
            val tags = item?.merged?.tags
            val games = item?.merged?.games
            val header = helper?.getView<View>(R.id.header)
            val subTag = helper?.getView<TextView>(R.id.sub_tag)
            if (tags != null && tags.isNotEmpty()) {
                header?.visibility = View.VISIBLE
                val tag = helper?.getView<TextView>(R.id.tag)
                tag?.text = tags[0].name
                subTag?.text = "的回答"
                helper?.setText(R.id.time, item.publish_time_before)
            } else if (games != null && games.isNotEmpty()) {
                header?.visibility = View.VISIBLE
                val tag = helper?.getView<TextView>(R.id.tag)
                tag?.text = games[0].title
                subTag?.text = "的评价"
                helper?.setText(R.id.time, item.publish_time_before)
            } else {
                header?.visibility = GONE
            }
        }

        /**
         * 渲染底部NavBar
         */
        private fun renderNavBar(helper: BaseViewHolder?, item: FeedModel?,
                                 voterCount: Int?, hasVote: Int? = 0,
                                 isFollow: Int? = 0,
                                 commentCount: Int? = 0, commentBtnTitle: String = "评论") {
            val voteView = helper?.getView<LinearLayout>(R.id.btn_vote)
            voteView?.visibility = if (voterCount == null) GONE else View.VISIBLE
            //todo 添加赞同按钮事件
            if (voterCount != null) {
                helper?.setText(R.id.tv_vote, "${if (hasVote == 0) "赞同" else "已赞"} $voterCount")
            }

            val followView = helper?.getView<LinearLayout>(R.id.btn_follower)
            followView?.visibility = if (isFollow == null) GONE else View.VISIBLE
            if (isFollow != null) {
                helper?.setText(R.id.tv_follow, if (isFollow == 0) "关注" else "已关注")
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