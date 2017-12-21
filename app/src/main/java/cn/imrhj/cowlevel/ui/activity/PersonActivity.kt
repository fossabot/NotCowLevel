package cn.imrhj.cowlevel.ui.activity

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.UserModel
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.utils.ScreenSizeUtil
import cn.imrhj.cowlevel.utils.StringUtils
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import cn.imrhj.cowlevel.utils.cdnImageForSize
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elvishew.xlog.XLog
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : BaseActivity() {
    private val DEFAULT_COVER = "https://pic1.cdncl.net/user/hexer/common_pic/b35960f11f45262807f0e081a4f90a99.jpg"

    private var mName = ""
    private var mAvatar = ""


    override fun layoutId(): Int? {
        return R.layout.activity_person
    }

    override fun initData() {
        mName = intent.getStringExtra("name")
        mAvatar = intent.getStringExtra("avatar")
        val urlSlug = intent.getStringExtra("url_slug")

        RetrofitManager.getInstance().getUser(urlSlug)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> processResult(result) },
                        { t -> XLog.e("class = PersonActivity rhjlog initData: $t") }
                )
    }

    override fun initView() {
        Glide.with(this)
                .load(cdnImageForDPSquare(mAvatar, 80))
                .apply(RequestOptions().circleCrop().placeholder(R.drawable.round_place_holder))
                .into(avatar)
        imageview.layoutParams.height = (ScreenSizeUtil.getScreenWidth() * 0.625f).toInt()
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    private fun processResult(user: UserModel) {
        val cover = if (StringUtils.isNotBlank(user.cover)) user.cover else DEFAULT_COVER
        Glide.with(this)
                .load(cdnImageForSize(cover, ScreenSizeUtil.getScreenWidth(), (ScreenSizeUtil.getScreenWidth() * 0.625f).toInt()))
                .into(imageview)
    }

}
