package cn.imrhj.cowlevel.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.util.Log
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.HtmlParseManager
import cn.imrhj.cowlevel.network.model.element.ElementHomeModel
import cn.imrhj.cowlevel.network.model.element.ElementModel
import cn.imrhj.cowlevel.ui.adapter.FragmentAdapter
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.ui.fragment.element.ElementArticleFragment
import cn.imrhj.cowlevel.ui.fragment.element.ElementFeedFragment
import cn.imrhj.cowlevel.ui.fragment.element.ElementQuestionFragment
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_element.*

class ElementActivity : BaseActivity() {
    private var mId: Int = -1
    private lateinit var mName: String
    private lateinit var mCover: String
    private lateinit var mElementData: ElementModel

    private val mFeedFragment by lazy {
        val fragment = ElementFeedFragment()
        val clazz = ElementFeedFragment::class.java
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

    private val mTitleList = arrayListOf("动态", "问题", "文章", "视频")

    private val mAdapter by lazy {
        FragmentAdapter(supportFragmentManager,
                arrayOf(mFeedFragment, mQuestionFragment, mArticleFragment),
                mTitleList
        )
    }

    override fun layoutId(): Int? {
        return R.layout.activity_element
    }

    override fun initView() {
        Glide.with(this)
                .load(cdnImageForDPSquare(mCover, 80))
                .into(avatar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { this.onBackPressed() }
        tabLayout.setupWithViewPager(viewpager)
        window.sharedElementEnterTransition.addListener(callEndTransitionListener(this::waitForAnimationEnd))
        toolbarLayout.title = mName
        viewpager.offscreenPageLimit = 5
    }

    private fun waitForAnimationEnd() {
        viewpager.adapter = mAdapter
    }

    override fun initData() {
        try {
            mId = intent.getStringExtra("id").toInt()
        } catch (e: Exception) {
            Log.e(Thread.currentThread().name, "class = ElementActivity rhjlog initView: $e")
        }
        mCover = intent.getStringExtra("cover")
        mName = intent.getStringExtra("name")
        HtmlParseManager.getElement(mId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    processHeaderData(it)
                }, {
                    Log.e(Thread.currentThread().name, "class = ElementActivity rhjlog initData: error: $it")
                })

    }

    override fun onBackPressed() {
        finishAfterTransition()
        overridePendingTransition(0, 0)
    }

    @SuppressLint("SetTextI18n")
    private fun processHeaderData(data: ElementHomeModel) {
        val element = data.element
        val relatedModel = data.related
        mFeedFragment.setRelatedData(relatedModel)
        subtitle.text = "${element?.followerCount} 人关注"
        desc.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(element?.content, 0) else
            Html.fromHtml(element?.content)
        if (element != null) {
            if (element.notShowGame == 0) {
                // 显示游戏
                mTitleList.add(1, "游戏")
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
        mAdapter.updateTitle(mTitleList)
        tabLayout.setupWithViewPager(viewpager)
    }
}
