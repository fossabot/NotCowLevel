package cn.imrhj.cowlevel.ui.activity

import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_FEED
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_USER
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.FeedModel
import cn.imrhj.cowlevel.network.model.UserModel
import cn.imrhj.cowlevel.ui.adapter.holder.FeedHolder
import cn.imrhj.cowlevel.ui.adapter.holder.UserHolder
import cn.imrhj.cowlevel.ui.animate.listener.callEndAnimatorListener
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.utils.ScreenSizeUtil
import cn.imrhj.cowlevel.utils.StringUtils
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import cn.imrhj.cowlevel.utils.cdnImageForSize
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_BOTTOM
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.chad.library.adapter.base.util.MultiTypeDelegate
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : BaseActivity() {
    private val DEFAULT_COVER = "https://pic1.cdncl.net/user/hexer/common_pic/534334eabc451d1c5a017020be6e4a76.jpg"

    private var mName = ""
    private var mAvatar = ""
    private val mAdapter = PersonAdapter(ArrayList())
    private var mIsAnimateRunning = false
    private var mNextCursor = 0
    private var mHasMore = true
    private var mIsShowNext = false
    private lateinit var mUrlSlug: String


    override fun layoutId(): Int? {
        return R.layout.activity_person
    }

    override fun initData() {
        mName = intent.getStringExtra("name")
        mAvatar = intent.getStringExtra("avatar")
        mUrlSlug = intent.getStringExtra("url_slug")

        val personObservable = RetrofitManager.getInstance().getUser(mUrlSlug)
        val personTimelineObservable = getFeedObservable(mUrlSlug)
        Observable.merge(personObservable, personTimelineObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it is UserModel) {
                        processHeaderResult(it)
                    } else {
                        mAdapter.addData(it as FeedModel)
                    }
                }, {
                    Log.e(Thread.currentThread().name, "class = PersonActivity rhjlog initData: error $it")
                }, {
                    //                    mAdapter.notifyDataSetChanged()
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
        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
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
        }

        Glide.with(this)
                .load(cdnImageForDPSquare(mAvatar, 80))
                .apply(RequestOptions().circleCrop().placeholder(R.drawable.round_place_holder))
                .into(avatar)
        imageview.layoutParams.height = (ScreenSizeUtil.getScreenWidth() * 0.625f).toInt()
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        val divider = DividerItemDecoration(recycler.context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.background_divider, null)!!)
        recycler.addItemDecoration(divider)
        mAdapter.openLoadAnimation(SLIDEIN_BOTTOM)
        mAdapter.setOnLoadMoreListener(this::loadNextPage, recycler)
        mAdapter.setLoadMoreView(object : LoadMoreView() {
            override fun getLayoutId(): Int {
                return R.layout.recycler_load_more
            }

            override fun getLoadingViewId(): Int {
                return R.id.load_more_loading_view
            }

            override fun getLoadEndViewId(): Int {
                return R.id.load_more_load_fail_view
            }

            override fun getLoadFailViewId(): Int {
                return R.id.load_more_load_end_view
            }

        })
        recycler.adapter = mAdapter
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ mAdapter.addData(it) }, {
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
        finishAfterTransition()
    }

    private fun processHeaderResult(user: UserModel) {
        val cover = if (StringUtils.isNotBlank(user.cover)) user.cover else DEFAULT_COVER
        Glide.with(this)
                .load(cdnImageForSize(cover, ScreenSizeUtil.getScreenWidth(), (ScreenSizeUtil.getScreenWidth() * 0.625f).toInt()))
                .into(imageview)
        mAdapter.addData(user)
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
