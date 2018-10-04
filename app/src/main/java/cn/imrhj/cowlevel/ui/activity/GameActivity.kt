package cn.imrhj.cowlevel.ui.activity

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.deeplink.AppDeepLink
import cn.imrhj.cowlevel.deeplink.WebDeepLink
import cn.imrhj.cowlevel.extensions.addNullableData
import cn.imrhj.cowlevel.extensions.bindLifecycle
import cn.imrhj.cowlevel.extensions.bindLifecycleOnMainThread
import cn.imrhj.cowlevel.manager.SchemeUtils
import cn.imrhj.cowlevel.network.manager.HtmlParseManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.UrlListModel
import cn.imrhj.cowlevel.network.model.game.GameHomeModel
import cn.imrhj.cowlevel.ui.activity.GameActivity.Companion.KEY_URL_SLUG
import cn.imrhj.cowlevel.ui.adapter.provider.game.GameHeaderProvider
import cn.imrhj.cowlevel.ui.adapter.provider.game.GameImageProvider
import cn.imrhj.cowlevel.ui.adapter.provider.game.GameStoreProvider
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.ui.view.recycler.TextLoadMoreView
import cn.imrhj.cowlevel.utils.cdnImageForFullWidthAndDPHeight
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.elvishew.xlog.XLog
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_game.*


@AppDeepLink("/game/{$KEY_URL_SLUG}")
@WebDeepLink("/game/{$KEY_URL_SLUG}")
class GameActivity : BaseActivity() {
    companion object {
        const val KEY_URL_SLUG = "url_slug"
        const val KEY_COVER = "cover"
        const val KEY_TITLE = "title"

        fun startGameActivity(urlSlug: String, cover: String?, title: String?) {
            val bundle = Bundle()
            bundle.putString(KEY_COVER, cover)
            bundle.putString(KEY_TITLE, title)
            bundle.putString(KEY_URL_SLUG, urlSlug)
            SchemeUtils.startActivity(GameActivity::class.java, bundle)

        }
    }

    private var mUrlSlug = ""
    private var mTitle = ""
    private var mCover = ""
    private val mAdapter by lazy { GameAdapter() }


    override fun layoutId(): Int? {
        return R.layout.activity_game
    }

    override fun initData() {
        if (isFromDeepLink()) {
            mUrlSlug = intent.extras?.getString(KEY_URL_SLUG, "") ?: ""
        } else {
            mUrlSlug = intent.getStringExtra(KEY_URL_SLUG)
            mTitle = intent.getStringExtra(KEY_TITLE)
            mCover = intent.getStringExtra(KEY_COVER)
        }
        getGameInfo()
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { this.onBackPressed() }
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        val divider = DividerItemDecoration(recycler.context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.background_divider, null)!!)
        recycler.addItemDecoration(divider)
        mAdapter.openLoadAnimation()
        mAdapter.setOnLoadMoreListener(this::loadNextPage, recycler)
        mAdapter.setLoadMoreView(TextLoadMoreView())
        recycler.adapter = mAdapter
    }

    private fun initTopView(cover: String?, title: String?) {
        Glide.with(this)
                .load(cdnImageForFullWidthAndDPHeight(cover, 280))
                .into(imageview)
    }

    private fun getGameInfo() {
        HtmlParseManager.getGame(mUrlSlug)
                .bindLifecycleOnMainThread(this)
                .subscribe({
                    initTopView(it.game?.cover, it.game?.chineseTitle)
                }, {
                    XLog.b().st(3).e("GameActivity getGameInfo:$it")

                })
    }

    private fun loadNextPage() {

    }

    inner class GameAdapter(data: MutableList<BaseModel>? = null) : MultipleItemRvAdapter<BaseModel, BaseViewHolder>(data) {
        init {
            finishInitialize()
        }

        override fun getViewType(t: BaseModel?): Int {
            return t?.getType()?.ordinal ?: ItemTypeEnum.TYPE_UNKNOWN.ordinal
        }

        override fun registerItemProvider() {
            mProviderDelegate.registerProvider(GameHeaderProvider())
            mProviderDelegate.registerProvider(GameImageProvider())
            mProviderDelegate.registerProvider(GameStoreProvider())

        }
    }
}
