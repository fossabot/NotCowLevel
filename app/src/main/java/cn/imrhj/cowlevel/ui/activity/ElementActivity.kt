package cn.imrhj.cowlevel.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Pair
import android.view.View
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.deeplink.AppDeepLink
import cn.imrhj.cowlevel.deeplink.WebDeepLink
import cn.imrhj.cowlevel.extensions.bindLifecycle
import cn.imrhj.cowlevel.extensions.parseHtml
import cn.imrhj.cowlevel.manager.SchemeUtils
import cn.imrhj.cowlevel.network.manager.HtmlParseManager
import cn.imrhj.cowlevel.network.model.element.ElementHomeModel
import cn.imrhj.cowlevel.network.model.element.ElementModel
import cn.imrhj.cowlevel.ui.activity.ElementActivity.Companion.KEY_ID
import cn.imrhj.cowlevel.ui.adapter.FragmentAdapter
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.ui.fragment.element.ElementArticleFragment
import cn.imrhj.cowlevel.ui.fragment.element.ElementFeedFragment
import cn.imrhj.cowlevel.ui.fragment.element.ElementGameFragment
import cn.imrhj.cowlevel.ui.fragment.element.ElementQuestionFragment
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.bumptech.glide.Glide
import com.elvishew.xlog.XLog
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_element.*

@AppDeepLink("/element/{$KEY_ID}")
@WebDeepLink("/element/{$KEY_ID}")
class ElementActivity : BaseActivity(), AAH_FabulousFragment.Callbacks {
    companion object {
        const val KEY_ID = "id"
        private const val KEY_COVER = "cover"
        private const val KEY_NAME = "name"
        private const val KEY_CACHE_SIZE = "cache_cover_size"
        fun startWithShareElement(avatar: View, pic: String?, name: String?, id: Int?, cacheSize: Int = 0) {
            val bundle = Bundle()
            bundle.putString(KEY_COVER, pic)
            bundle.putString(ElementActivity.KEY_NAME, name)
            bundle.putInt(ElementActivity.KEY_ID, id ?: -1)
            bundle.putInt(ElementActivity.KEY_CACHE_SIZE, cacheSize)
            SchemeUtils.startActivityTransition(ElementActivity::class.java, bundle,
                    Pair.create(avatar, "cover")
            )
        }
    }

    override fun onResult(result: Any?) {
        XLog.d("class = ElementActivity onResult: $result")
    }

    private var mId: Int = -1
    private lateinit var mName: String
    private lateinit var mCover: String
    private var mUseTransition: Boolean = true
    private lateinit var mElementData: ElementModel
    private var mCacheSize = 0

    private val mFeedFragment by lazy {
        val fragment = ElementFeedFragment()
        fragment.mId = mId
        fragment
    }
    private val mQuestionFragment by lazy {
        val fragment = ElementQuestionFragment()
        fragment.mId = mId
        fragment
    }
    private val mArticleFragment by lazy {
        val fragment = ElementArticleFragment()
        fragment.mId = mId
        fragment
    }

    private val mGameFragment by lazy {
        val fragment = ElementGameFragment()
        fragment.mId = mId
        fragment
    }

    private val mTitleList = arrayListOf("动态")
    private val mFragmentList by lazy {
        arrayListOf<Fragment>(mFeedFragment)
    }

    private val mAdapter by lazy {
        FragmentAdapter(supportFragmentManager, mFragmentList, mTitleList)
    }

//    private val mOnPageChangeListener by lazy {
//        object : ViewPager.SimpleOnPageChangeListener() {
//            override fun onPageSelected(position: Int) {
//                if (position == 1) {
//                    fab_filter.show()
//                } else {
//                    fab_filter.hide()
//                }
//            }
//        }
//    }

    override fun layoutId(): Int? {
        return R.layout.activity_element
    }

    override fun initView() {
        if (!isFromDeepLink()) {
            initHeaderView()
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { this.onBackPressed() }
        tabLayout.setupWithViewPager(viewpager)
        if (mUseTransition) {
            window.sharedElementEnterTransition.addListener(callEndTransitionListener(this::waitForAnimationEnd))
        } else {
            waitForAnimationEnd()
        }
        viewpager.offscreenPageLimit = 5
//        fab_filter.setOnClickListener {
//            val dialogFragment = GameElementFilterFabFragment()
//            dialogFragment.setParentFab(fab_filter)
//            dialogFragment.show(supportFragmentManager, dialogFragment.tag)
//        }

    }

    private fun waitForAnimationEnd() {
        viewpager.adapter = mAdapter
    }

    private fun initHeaderView() {
        XLog.d("class = ElementActivity initHeaderView: ")
        Glide.with(this)
                .load(cdnImageForDPSquare(mCover, 80))
                .thumbnail(if (mCacheSize > 0)
                    Glide.with(this).load(cdnImageForDPSquare(mCover, mCacheSize)) else
                    null)
                .into(avatar)
        toolbarLayout.title = mName
    }

    override fun initData() {
        if (isFromDeepLink()) {
            mId = intent.extras?.getString(KEY_ID, "-1")?.toInt() ?: -1
            mUseTransition = false
        } else {
            mId = intent.getIntExtra(KEY_ID, -1)
            if (mId == -1) {
                XLog.t().b().st(3).e("class = ElementActivity initData: ID error")
            }
            mCover = intent.getStringExtra(KEY_COVER)
            mName = intent.getStringExtra(KEY_NAME)
            mCacheSize = intent.getIntExtra(KEY_CACHE_SIZE, 0)

            mUseTransition = intent.getBooleanExtra("useTransition", true)
        }
        HtmlParseManager.getElement(mId)
                .observeOn(AndroidSchedulers.mainThread())
                .bindLifecycle(this)
                .subscribe({
                    processHeaderData(it)
                }, {
                    Log.e(Thread.currentThread().name, "class = ElementActivity rhjlog initData: error: $it")
                })
    }

    override fun onBackPressed() {
        if (mUseTransition) {
            finishAfterTransition()
            overridePendingTransition(0, 0)
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processHeaderData(data: ElementHomeModel) {
        val element = data.element
        if (isFromDeepLink()) {
            mName = element?.name ?: ""
            mCover = element?.pic ?: ""
            initHeaderView()
        }

        val relatedModel = data.related
        mFeedFragment.setRelatedData(relatedModel)
        subtitle.text = "${element?.followerCount} 人关注"
        et_desc.text = element?.content?.parseHtml()
        if (element != null) {
            if (element.notShowGame == 0) {
//                viewpager.addOnPageChangeListener(mOnPageChangeListener)
                // 显示游戏
                mTitleList.addAll(listOf("游戏", "问题", "文章", "视频"))
                mFragmentList.addAll(listOf(mGameFragment, mQuestionFragment, mArticleFragment))
                if (element.postCount > 0) {
                    mTitleList[1] = "游戏 (${element.postCount})"
                }
                if (element.questionCount > 0) {
                    mTitleList[2] = "问题 (${element.questionCount})"
                }
                if (element.articleCount > 0) {
                    mTitleList[3] = "文章 (${element.articleCount})"
                }
                if (element.videoCount > 0) {
                    mTitleList[4] = "视频 (${element.videoCount})"
                }
            } else {
                // 隐藏游戏
                mTitleList.addAll(listOf("问题", "文章", "视频"))
                mFragmentList.addAll(listOf(mQuestionFragment, mArticleFragment))
                if (element.questionCount > 0) {
                    mTitleList[1] = "问题 (${element.questionCount})"
                }
                if (element.articleCount > 0) {
                    mTitleList[2] = "文章 (${element.articleCount})"
                }
                if (element.videoCount > 0) {
                    mTitleList[3] = "视频 (${element.videoCount})"
                }
            }
        }
        mAdapter.update(mTitleList, mFragmentList)
        tabLayout.setupWithViewPager(viewpager)
    }
}
