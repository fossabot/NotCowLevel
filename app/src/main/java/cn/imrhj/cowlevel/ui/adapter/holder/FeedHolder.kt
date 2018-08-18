package cn.imrhj.cowlevel.ui.adapter.holder

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.CowLinks
import cn.imrhj.cowlevel.extensions.setTextAndShow
import cn.imrhj.cowlevel.manager.SchemeUtils
import cn.imrhj.cowlevel.network.manager.COW_LEVEL_URL
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.FeedModel
import cn.imrhj.cowlevel.network.model.FeedModel.Type.*
import cn.imrhj.cowlevel.network.model.GameModel
import cn.imrhj.cowlevel.network.model.ShareLinkModel
import cn.imrhj.cowlevel.ui.activity.PersonActivity
import cn.imrhj.cowlevel.ui.adapter.DP130_2PX
import cn.imrhj.cowlevel.ui.adapter.DP65_2PX
import cn.imrhj.cowlevel.utils.ResourcesUtils
import cn.imrhj.cowlevel.utils.ScreenSizeUtil.dp2px
import cn.imrhj.cowlevel.utils.StringUtils
import cn.imrhj.cowlevel.utils.cdnImageForSize
import cn.imrhj.cowlevel.utils.cdnImageForSquare
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import java.lang.ref.WeakReference

/**
 * FeedHolder 处理一些界面绑定逻辑信息
 * Created by rhj on 20/12/2017.
 */

class FeedHolder() {
    constructor(fragment: Fragment) : this() {
        mFragment = WeakReference(fragment)
    }

    constructor(activity: Activity) : this() {
        mActivity = WeakReference(activity)
    }

    private var mFragment: WeakReference<Fragment>? = null
    private var mActivity: WeakReference<Activity>? = null

    private fun getActivity(): Activity {
        return mActivity?.get() ?: App.app.getLastActivity()
    }

    private fun getFragment(): Fragment? {
        return mFragment?.get()
    }

    private fun getGlide(): RequestManager {
        return if (mFragment != null) Glide.with(getFragment()) else Glide.with(getActivity())
    }

    fun renderCommon(helper: BaseViewHolder, item: FeedModel?) {
        renderHeader(helper, item)
        renderUser(helper, item)
        val vPic = helper.getView<ImageView>(R.id.pic)
        val vTitle = helper.getView<TextView>(R.id.title)
        val vContent = helper.getView<TextView>(R.id.content)
        val vThumb = helper.getView<ImageView>(R.id.thumb)
        val vGameLayout = helper.getView<View>(R.id.layout_game)
        helper.getView<View>(R.id.layout_link)?.visibility = GONE
        helper.getView<View>(R.id.feed_card)?.setOnClickListener(null)
        helper.getView<ViewGroup>(R.id.ll_dynamic)?.removeAllViews()
        vGameLayout?.visibility = GONE
        vPic?.visibility = GONE
        vTitle?.visibility = GONE
        vContent?.visibility = GONE
        vThumb?.visibility = GONE
        if (item != null) {
            when (item.action) {
                editor_elite_article.name -> this.renderEditorEliteArticle(helper, item)
                editor_elite_answer.name -> this.renderTagAnswer(helper, item)
                editor_elite_review.name -> this.renderEditorEliteReview(helper, item)          //评价了游戏
                tag_answer.name -> this.renderTagAnswer(helper, item)
                tag_article.name -> this.renderEditorEliteArticle(helper, item)
                tag_sharelink.name -> this.renderTagShareLink(helper, item)
                tag_question.name -> this.renderTagQuestion(helper, item)
                vote_article.name -> this.renderEditorEliteArticle(helper, item)
                follow_question.name -> this.renderFollowQuestion(helper, item)
                submit_question.name -> this.renderSubmitQuestion(helper, item)
                sharelink_add.name -> this.renderTagShareLink(helper, item)
                post.name -> this.renderPost(helper, item)
                post_submit_answer.name -> this.renderTagAnswer(helper, item)
                post_submit_review.name -> this.renderPostSubmitReview(helper, item)
                post_submit_sharelink.name -> this.renderTagShareLink(helper, item)
                post_submit_question.name -> this.renderSubmitQuestion(helper, item)
                post_submit_article.name -> this.renderEditorEliteArticle(helper, item)         //发布了文章
            }
        }
    }

    private fun renderTagShareLink(helper: BaseViewHolder?, item: FeedModel) {
        renderContent(helper, item.comment?.content)
        renderLink(helper, item.sharelink)
        helper?.getView<View>(R.id.nav_voter_bar)?.visibility = GONE
    }

    private fun renderPostSubmitReview(helper: BaseViewHolder?, item: FeedModel) {
        renderGame(helper, item.game)
        renderTitle(helper, item.review?.title)
        renderContent(helper, item.review?.neat_content?.desc)
        renderThumb(helper, item.review?.neat_content?.thumb)
        renderNavBar(helper, item, item.review?.vote_count, item.review?.has_vote, null,
                item.review?.comment_count)
    }

    private fun renderPost(helper: BaseViewHolder?, item: FeedModel) {
        renderGame(helper, item.merged?.games)
    }

    private fun renderSubmitQuestion(helper: BaseViewHolder?, item: FeedModel) {
        this.renderTitle(helper, item.question?.title)
        this.renderContent(helper, item.question?.neat_content?.desc)
        this.renderThumb(helper, item.question?.neat_content?.thumb)
    }

    private fun renderFollowQuestion(helper: BaseViewHolder?, item: FeedModel) {
        this.renderTitle(helper, item.question?.title)
        this.renderContent(helper, item.question?.content)
    }

    private fun renderTagAnswer(helper: BaseViewHolder?, item: FeedModel) {
        val answer = item.answer
        val question = item.question
        renderTitle(helper, question?.title, "https://cowlevel.net/question/${question?.id}")
        renderContent(helper, answer?.neat_content?.desc)
        renderThumb(helper, answer?.neat_content?.thumb)
        renderNavBar(helper, item, answer?.vote_count, answer?.has_vote, question?.is_follow,
                question?.comment_count)
        setUrl(helper, "https://cowlevel.net/question/${question?.id}/answer/${answer?.id}")
    }

    private fun renderTagQuestion(helper: BaseViewHolder?, item: FeedModel) {
        val question = item.question
        renderNavBar(helper, item, null, 0, question?.is_follow!!,
                question.answer_count)
        renderTitle(helper, question.title)
        renderContent(helper, question.neat_content?.desc)
        renderThumb(helper, question.neat_content?.thumb)
        setUrl(helper, "https://cowlevel.net/question/${question.id}")
    }

    private fun renderEditorEliteArticle(helper: BaseViewHolder?, item: FeedModel) {
        val article = item.article
        renderTitle(helper, article?.title)
        renderContent(helper, article?.neat_content?.desc)
        renderThumb(helper, article?.neat_content?.thumb)
        renderPic(helper, article?.pic)
        renderNavBar(helper, item, article?.vote_count, article?.has_vote, null,
                article?.comment_count)
        setUrl(helper, "https://cowlevel.net/article/${article?.id}")
    }

    private fun setUrl(helper: BaseViewHolder?, url: String) {
        helper?.getView<View>(R.id.feed_card)?.setOnClickListener { SchemeUtils.openLink(url) }
    }

    private fun renderEditorEliteReview(helper: BaseViewHolder?, item: FeedModel) {
        val review = item.review ?: return
        renderContent(helper, review.neat_content?.desc)
        renderThumb(helper, review.neat_content?.thumb)
        renderNavBar(helper, item, review.vote_count, review.has_vote, null,
                review.comment_count, "评论",
                { this.voteReview(review.has_vote, review.id) })
        renderGame(helper, item.game)
        setUrl(helper, "https://cowlevel.net/game/${item.game?.url_slug}/review/${review.id}")
    }


    private fun renderTitle(helper: BaseViewHolder?, title: String?, link: String? = null) {
        val titleView = helper?.getView<TextView>(R.id.title)
        titleView?.setTextAndShow(title)
        if (link != null) {
            titleView?.setOnClickListener { SchemeUtils.openLink(link) }
        }
    }

    private fun renderContent(helper: BaseViewHolder?, content: String?) {
        helper?.getView<TextView>(R.id.content)?.setTextAndShow(content)
    }

    private fun renderThumb(helper: BaseViewHolder?, thumb: String?) {
        if (StringUtils.isNotBlank(thumb)) {
            val view = helper?.getView<ImageView>(R.id.thumb)
            view?.visibility = VISIBLE
            Glide.with(App.app)
                    .load(cdnImageForSquare(thumb, dp2px(100F)))
                    .into(view)
        }
    }

    private fun renderPic(helper: BaseViewHolder?, pic: String?) {
        if (StringUtils.isNotBlank(pic)) {
            val imageView = helper?.getView<ImageView>(R.id.pic)
            imageView?.visibility = VISIBLE
            imageView?.post {
                Glide.with(App.app)
                        .load(cdnImageForSize(pic, imageView.width, imageView.height))
                        .into(imageView)
            }
        }
    }

    private fun renderGame(helper: BaseViewHolder?, game: GameModel?, hideOuter: Boolean = false) {
        val layout = helper?.getView<LinearLayout>(R.id.ll_dynamic)
        val gameView = LayoutInflater.from(App.app)
                .inflate(R.layout.item_game_layout, layout, false)
        if (hideOuter) {
            gameView?.background = null
            gameView?.setPadding(0, 0, 0, 0)
        } else {
            gameView?.background = ColorDrawable(ResourcesUtils.getColor(R.color.colorWhite1))
            gameView?.setPadding(dp2px(12), dp2px(20), dp2px(12),
                    dp2px(20))
        }
//
        if (game != null) {
            val platforms = game.platform_support_list
            gameView.findViewById<TextView>(R.id.game_title)?.text = game.title
            gameView.findViewById<TextView>(R.id.game_time)?.text = game.game_publish_date_show
            gameView.findViewById<TextView>(R.id.game_platforms)?.text =
                    if (platforms?.isNotEmpty() == true)
                        platforms.map { it.name }.reduce { n1, n2 -> "$n1 / $n2" }
                    else ""
            getGlide().load(cdnImageForSize(game.pic, DP130_2PX, DP65_2PX))
                    .into(gameView.findViewById(R.id.game_pic))

            gameView.setOnClickListener {
                SchemeUtils.openLink(COW_LEVEL_URL + "game/" + game.url_slug)
            }

            layout?.addView(gameView)
            (gameView.layoutParams as LinearLayout.LayoutParams).topMargin = dp2px(12)
        }
    }

    private fun renderGame(helper: BaseViewHolder?, games: List<GameModel>?) {
        games?.forEach { renderGame(helper, it, true) }
    }

    private fun renderLink(helper: BaseViewHolder?, shareLink: ShareLinkModel?) {
        if (shareLink != null) {
            helper?.getView<View>(R.id.layout_link)?.visibility = View.VISIBLE
            helper?.getView<TextView>(R.id.tv_link_title)?.text = shareLink.title
            getGlide().load(cdnImageForSize(shareLink.pic, DP130_2PX, DP65_2PX))
                    .into(helper?.getView(R.id.iv_link_pic))
            helper?.getView<View>(R.id.layout_link)?.setOnClickListener {
                SchemeUtils.openLink(CowLinks.getShareLink(shareLink.id))
            }
        }
    }

    /**
     * 渲染顶部的tag
     */
    private fun renderHeader(helper: BaseViewHolder?, item: FeedModel?) {
        val voters = item?.merged?.voters
        val followers = item?.merged?.followers
        val games = item?.merged?.games
        val header = helper?.getView<View>(R.id.header)
        val tags = item?.merged?.tags

        var frontValue = "来自"
        var tagValue = when {
            voters?.isNotEmpty() == true && voters[0].name?.isNotBlank() == true -> voters[0].name
            followers?.isNotEmpty() == true && followers[0].name?.isNotBlank() == true -> followers[0].name
            games?.isNotEmpty() == true && games[0].chinese_title?.isNotBlank() == true -> games[0].chinese_title
            tags?.isNotEmpty() == true && tags[0].name?.isNotBlank() == true -> tags[0].name
            else -> ""
        }

        var subValue = "的回答"
        when (item?.action) {
            follow_question.name -> {
                frontValue = ""
                tagValue = followers?.get(0)?.name ?: ""
                subValue = "关注了该问题"
            }
            vote_article.name -> {
                frontValue = ""
                tagValue = voters?.get(0)?.name ?: ""
                subValue = "等赞同了该文章"
            }
            tag_article.name -> {
                tagValue = tags?.get(0)?.name ?: ""
                subValue = "的文章"
            }
            post_submit_review.name -> {
                tagValue = games?.get(0)?.chinese_title ?: ""
                subValue = "的评价"
            }
            tag_question.name -> {
                tagValue = tags?.get(0)?.name ?: ""
                subValue = "的问题"
            }
            post_submit_question.name -> {
                tagValue = games?.get(0)?.chinese_title ?: ""
                subValue = "的问题"
            }
            post_submit_article.name -> {
                tagValue = games?.get(0)?.chinese_title ?: ""
                subValue = "的文章"
            }
            tag_answer.name -> {
//                tagValue = tags?.get(0)?.name ?: ""
            }

        }

        if (tagValue!!.isNotBlank()) {
            header?.visibility = View.VISIBLE
            helper?.setText(R.id.tag, tagValue)
            helper?.setText(R.id.sub_tag, subValue)
            helper?.setText(R.id.front_tag, frontValue)
            helper?.setText(R.id.time, item?.publish_time_before)
        } else {
            header?.visibility = GONE
        }
    }

    /**
     * 渲染底部NavBar
     */
    private fun renderNavBar(helper: BaseViewHolder?, item: FeedModel, voterCount: Int?,
                             hasVote: Int? = 0, isFollow: Int? = 0, commentCount: Int? = 0,
                             commentBtnTitle: String = "评论", voteCallback: (() -> Unit)? = null) {
        helper?.getView<View>(R.id.nav_voter_bar)?.visibility = VISIBLE
        val voteView = helper?.getView<LinearLayout>(R.id.btn_vote)
        voteView?.visibility = if (voterCount == null) GONE else VISIBLE
        voteView?.setOnClickListener { voteCallback?.invoke() }
        if (voterCount != null) {
            helper?.setText(R.id.tv_vote, "${if (hasVote == 0) "赞同" else "已赞"} $voterCount")
            voteView?.setBackgroundResource(if (hasVote == 0) R.drawable.background_button_unactive
            else R.drawable.background_button_red)
//            DrawableHelper.withDrawable(R.drawable.icon_triangle_up)
//                    .setColor(if (hasVote == 0) R.color.white else R.color.colorRedAccept)
//                    .applyTo(helper?.getView(R.id.iv_vote)!!)

        }
        val followView = helper?.getView<LinearLayout>(R.id.btn_follower)
        followView?.visibility = if (isFollow == null) GONE else VISIBLE
        if (isFollow != null) {
            helper?.setText(R.id.tv_follow, if (isFollow == 0) "关注" else "已关注")
            followView?.setBackgroundResource(if (isFollow == 0) R.drawable.background_button_unactive
            else R.drawable.background_button_red)
        }

        helper?.setText(R.id.tv_commit, "$commentBtnTitle $commentCount")
    }

    /**
     * 渲染用户信息
     */
    private fun renderUser(helper: BaseViewHolder?, item: FeedModel?) {
        val user = item?.user
        if (user != null) {
            val avatarView = helper?.getView<ImageView>(R.id.avatar)
            helper?.getView<View>(R.id.user)?.visibility = VISIBLE

            getGlide().`as`(if (user.avatar?.endsWith(".gif") != false)
                GifDrawable::class.java else Bitmap::class.java)
                    .load(cdnImageForSquare(user.avatar, dp2px(48F)))
                    .apply(RequestOptions().circleCrop().placeholder(R.drawable.round_place_holder))
                    .into(avatarView)

            helper?.setText(R.id.name, user.name)
            helper?.setText(R.id.intro, user.intro)
            helper?.setText(R.id.subtitle, item.action_text)

            helper?.getView<View>(R.id.user)?.setOnClickListener {
                val activity = App.app.getLastActivity()
                val intent = Intent(activity, PersonActivity::class.java)
                intent.putExtra("avatar", user.avatar)
                intent.putExtra("name", user.name)
                intent.putExtra("url_slug", user.url_slug)
                val options = ActivityOptions.makeSceneTransitionAnimation(activity,
                        Pair.create(avatarView, "avatar"))
                avatarView?.transitionName = "avatar"
                activity.startActivity(intent, options.toBundle())
                activity.overridePendingTransition(0, 0)
            }
        } else {
            helper?.getView<View>(R.id.user)?.visibility = GONE
        }
    }

    /**
     * 对游戏评价进行点赞/取消点赞
     * @param hasVote 是否点过赞了
     */
    private fun voteReview(hasVote: Int, id: Int) {
        val observable = if (hasVote == 0)
            RetrofitManager.getInstance().voteReview(id) else
            RetrofitManager.getInstance().unvoteReview(id)
        observable.subscribe({
            Log.d(Thread.currentThread().name, "class = FeedHolder rhjlog voteReview: onNext")
        }, {
            Log.d(Thread.currentThread().name, "class = FeedHolder rhjlog voteReview: onError +$it")
        })
    }

}