package cn.imrhj.cowlevel.ui.activity

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.utils.cdnImageForFullWidthAndDPHeight
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_game.*

val KEY_URL_SLUG = "url_slug"
val KEY_COVER = "cover"
val KEY_TITLE = "title"

class GameActivity : BaseActivity() {
    private var mUrlSlug = ""
    private var mTitle = ""
    private var mCover = ""
    override fun layoutId(): Int? {
        return R.layout.activity_game
    }

    override fun initData() {
        mUrlSlug = intent.getStringExtra(KEY_URL_SLUG)
        mTitle = intent.getStringExtra(KEY_TITLE)
        mCover = intent.getStringExtra(KEY_COVER)
    }

    override fun initView() {
        Glide.with(this)
                .load(cdnImageForFullWidthAndDPHeight(mCover, 280))
                .into(imageview)
    }
}
