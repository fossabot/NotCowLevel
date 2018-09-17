package cn.imrhj.cowlevel.ui.activity

import android.os.Bundle
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.deeplink.AppDeepLink
import cn.imrhj.cowlevel.deeplink.WebDeepLink
import cn.imrhj.cowlevel.manager.SchemeUtils
import cn.imrhj.cowlevel.ui.activity.GameActivity.Companion.KEY_URL_SLUG
import cn.imrhj.cowlevel.ui.base.BaseActivity


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
    }

    override fun initView() {
//        Glide.with(this)
//                .load(cdnImageForFullWidthAndDPHeight(mCover, 280))
//                .into(imageview)
    }
}
