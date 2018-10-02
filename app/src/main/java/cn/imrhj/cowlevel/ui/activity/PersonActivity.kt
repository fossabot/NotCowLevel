package cn.imrhj.cowlevel.ui.activity

import android.support.design.widget.AppBarLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewAnimationUtils
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_FEED
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_USER
import cn.imrhj.cowlevel.deeplink.AppDeepLink
import cn.imrhj.cowlevel.deeplink.WebDeepLink
import cn.imrhj.cowlevel.extensions.bindLifecycle
import cn.imrhj.cowlevel.extensions.bindLifecycleOnMainThread
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.UserModel
import cn.imrhj.cowlevel.network.model.feed.FeedModel
import cn.imrhj.cowlevel.ui.adapter.holder.FeedHolder
import cn.imrhj.cowlevel.ui.adapter.holder.UserHolder
import cn.imrhj.cowlevel.ui.animate.listener.callEndAnimatorListener
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.ui.view.recycler.TextLoadMoreView
import cn.imrhj.cowlevel.utils.ScreenSizeUtil
import cn.imrhj.cowlevel.utils.StringUtils
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import cn.imrhj.cowlevel.utils.cdnImageForFullWidthAndPXHeight
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_BOTTOM
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.elvishew.xlog.XLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_person.*

@AppDeepLink("/people/{url_slug}")
@WebDeepLink("/people/{url_slug}")
class PersonActivity : BaseActivity() {
    private val DEFAULT_COVER = "https://pic1.cdncl.net/user/hexer/common_pic/534334eabc451d1c5a017020be6e4a76.jpg"
    private var mAvatar = ""
    private val mAdapter = PersonAdapter(ArrayList())
    private var mIsAnimateRunning = false
    private var mNextCursor = 0
    private var mHasMore = true
    private var mIsShowNext = false
    private lateinit var mUrlSlug: String
    private val HEADER_COVER_HEIGHT by lazy { (ScreenSizeUtil.getScreenWidth() * 0.625f).toInt() }


    override fun layoutId(): Int? {
        return R.layout.activity_person
    }

    override fun initData() {
        if (isFromDeepLink()) {
            mUrlSlug = intent.extras?.getString("url_slug", "") ?: ""
        } else {
            mAvatar = intent.getStringExtra("avatar")
            mUrlSlug = intent.getStringExtra("url_slug")
        }

        val personObservable = RetrofitManager.getInstance().getUser(mUrlSlug)
        val personTimelineObservable = getFeedObservable(mUrlSlug)
        Observable.merge(personObservable, personTimelineObservable)
                .bindLifecycleOnMainThread(this)
                .subscribe({
                    if (it is UserModel) {
                        processHeaderResult(it)
                    } else {
                        mAdapter.addData(it as FeedModel)
                    }
                }, {
                    XLog.t().b().st(3).e("class = PersonActivity initData: $it")
                })
    }

    private fun getFeedObservable(urlSlug: String, nextCursor: Int = 0): Observable<FeedModel> {
        return RetrofitManager.getInstance().getUserTimeLine(urlSlug, nextCursor)
                .doOnNext {
                    mNextCursor = it.last_id
                    mHasMore = it.has_more == 1
                }
                .flatMap { Observable.fromIterable(it.list) }

    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { this.onBackPressed() }
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange - cardAvatar.measuredWidth / 2) {
                // 折叠
                if (cardAvatar.visibility == View.VISIBLE && !mIsAnimateRunning) {
                    mIsAnimateRunning = true
                    startAvatarAnimate(false)
                }
            } else {
                if (cardAvatar.visibility == View.INVISIBLE && !mIsAnimateRunning) {
                    mIsAnimateRunning = true
                    startAvatarAnimate(true)
                }
            }
        })

        initViewAfter()

        imageview.layoutParams.height = HEADER_COVER_HEIGHT
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        val divider = DividerItemDecoration(recycler.context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.background_divider, null)!!)
        recycler.addItemDecoration(divider)
        mAdapter.openLoadAnimation(SLIDEIN_BOTTOM)
        mAdapter.setOnLoadMoreListener(this::loadNextPage, recycler)
        mAdapter.setLoadMoreView(TextLoadMoreView())
        recycler.adapter = mAdapter
    }

    override fun initViewAfter() {
        Glide.with(this)
                .load(cdnImageForDPSquare(mAvatar, 80))
                .thumbnail(Glide.with(this)
                        .load(cdnImageForDPSquare(mAvatar, 48)))
                .apply(RequestOptions().circleCrop().placeholder(R.drawable.round_place_holder))
                .into(avatar)
    }

    private fun loadNextPage() {
        if (!mHasMore) {
            mAdapter.loadMoreEnd()
            return
        }
        this.loadFeed(this.mNextCursor)
        mIsShowNext = true
    }

    private fun loadFeed(nextCursor: Int) {
        getFeedObservable(mUrlSlug, nextCursor)
                .bindLifecycleOnMainThread(this)
                .subscribe({
                    mAdapter.addData(it)
                }, {
                    //todo show error
                    if (mIsShowNext) {
                        mAdapter.loadMoreFail()
                        mIsShowNext = false
                    }
                }, {
                    if (mIsShowNext) {
                        if (mHasMore) {
                            mAdapter.loadMoreComplete()
                        } else {
                            mAdapter.loadMoreEnd()
                        }
                        mIsShowNext = false
                    }
                })

    }

    override fun onBackPressed() {
        if (isFromDeepLink()) {
            super.onBackPressed()
        } else {
            finishAfterTransition()
        }
    }

    private fun processHeaderResult(user: UserModel) {
        val cover = if (StringUtils.isNotBlank(user.cover)) user.cover else DEFAULT_COVER
        if (isFromDeepLink()) {
            mAvatar = user.avatar ?: ""
            initViewAfter()
        }
        Glide.with(this)
                .load(cdnImageForFullWidthAndPXHeight(cover, HEADER_COVER_HEIGHT))
                .into(imageview)
        mAdapter.addData(0, user)
    }

    private fun startAvatarAnimate(show: Boolean) {
        val centerX = cardAvatar.measuredWidth / 2
        val centerY = cardAvatar.measuredHeight / 2
        val startRadius = if (show) 0f else (cardAvatar.measuredWidth / 2).toFloat()
        val endRadius = if (show) (cardAvatar.measuredWidth / 2).toFloat() else 0f
        val animator = ViewAnimationUtils.createCircularReveal(cardAvatar, centerX, centerY, startRadius, endRadius)
        animator.duration = 300
        animator.addListener(callEndAnimatorListener {
            if (!show) {
                cardAvatar.visibility = View.INVISIBLE
            }
            mIsAnimateRunning = false
        })
        if (show) {
            cardAvatar.visibility = View.VISIBLE
        }
        animator.start()
    }

    inner class PersonAdapter(data: MutableList<BaseModel>) : BaseQuickAdapter<BaseModel, BaseViewHolder>(data) {
        private val feedHolder = FeedHolder(this@PersonActivity)
        private val userHolder = UserHolder()

        init {
            multiTypeDelegate = object : MultiTypeDelegate<BaseModel>() {
                override fun getItemType(t: BaseModel?): Int {
                    if (t != null) {
                        return t.getType().ordinal
                    }
                    return -1
                }
            }
            multiTypeDelegate
                    .registerItemType(TYPE_FEED.ordinal, R.layout.item_feed_common)
                    .registerItemType(TYPE_USER.ordinal, R.layout.item_person_header)

        }

        override fun convert(helper: BaseViewHolder?, item: BaseModel?) {
            if (helper != null) {
                when (ItemTypeEnum.valueOf(helper.itemViewType)) {
                    TYPE_FEED -> feedHolder.renderCommon(helper, item as FeedModel)
                    TYPE_USER -> userHolder.renderHeader(helper, item as UserModel)
                }
            }
        }
    }
}
