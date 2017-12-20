package cn.imrhj.cowlevel.ui.activity

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : BaseActivity() {
    private var mName = ""
    private var mAvatar = ""
    override fun layoutId(): Int? {
        return R.layout.activity_person
    }

    override fun initData() {
        mName = intent.getStringExtra("name")
        mAvatar = intent.getStringExtra("avatar")

    }

    override fun initView() {
        Glide.with(this)
                .load(cdnImageForDPSquare(mAvatar, 80))
                .apply(RequestOptions().circleCrop().placeholder(R.drawable.round_place_holder))
                .into(avatar)
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

}
