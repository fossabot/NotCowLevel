package cn.imrhj.cowlevel.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.transition.Transition
import android.util.Log
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.HtmlParseManager
import cn.imrhj.cowlevel.network.model.element.ElementHomeModel
import cn.imrhj.cowlevel.network.model.element.ElementModel
import cn.imrhj.cowlevel.ui.adapter.FragmentAdapter
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.ui.fragment.ElementFeedFragment
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
        fragment.mId = mId
        fragment
    }

    override fun layoutId(): Int? {
        return R.layout.activity_element
    }

    override fun initView() {
        name.text = mName
        Glide.with(this)
                .load(cdnImageForDPSquare(mCover, 80))
                .into(avatar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { this.onBackPressed() }
        tabLayout.setupWithViewPager(viewpager)
        window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                waitForAnimationEnd()
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }
        })

    }

    fun waitForAnimationEnd() {
        viewpager.adapter = FragmentAdapter(supportFragmentManager,
                arrayOf(mFeedFragment),
                arrayOf("动态", "问题", "文章", "视频")
        )

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

    }
}
