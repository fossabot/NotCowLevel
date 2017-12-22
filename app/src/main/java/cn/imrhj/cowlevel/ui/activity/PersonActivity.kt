package cn.imrhj.cowlevel.ui.activity

import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.*
import cn.imrhj.cowlevel.ui.adapter.holder.FeedHolder
import cn.imrhj.cowlevel.ui.adapter.holder.UserHolder
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
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.elvishew.xlog.XLog
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : BaseActivity() {
    private val DEFAULT_COVER = "https://pic1.cdncl.net/user/hexer/common_pic/b35960f11f45262807f0e081a4f90a99.jpg"

    private var mName = ""
    private var mAvatar = ""
    private val mAdapter = PersonAdapter(ArrayList())


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
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        val divider = DividerItemDecoration(recycler.context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.background_divider, null)!!)
        recycler.addItemDecoration(divider)
        mAdapter.openLoadAnimation(SLIDEIN_BOTTOM)
        recycler.adapter = mAdapter
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    private fun processResult(user: UserModel) {
        val cover = if (StringUtils.isNotBlank(user.cover)) user.cover else DEFAULT_COVER
        Glide.with(this)
                .load(cdnImageForSize(cover, ScreenSizeUtil.getScreenWidth(), (ScreenSizeUtil.getScreenWidth() * 0.625f).toInt()))
                .into(imageview)
        mAdapter.addData(user)
    }

    inner class PersonAdapter(data: MutableList<BaseModel>) : BaseQuickAdapter<BaseModel, BaseViewHolder>(data) {
        private val feedHolder = FeedHolder(this@PersonActivity)
        private val userHolder = UserHolder()

        init {
            multiTypeDelegate = object : MultiTypeDelegate<BaseModel>() {
                override fun getItemType(t: BaseModel?): Int {
                    if (t != null) {
                        return t.getType()
                    }
                    return -1
                }
            }
            multiTypeDelegate.registerItemType(TYPE_FEED, R.layout.item_feed_common)
                    .registerItemType(TYPE_USER, R.layout.item_person_header)

        }

        override fun convert(helper: BaseViewHolder?, item: BaseModel?) {
            when (helper?.itemViewType) {
                TYPE_FEED -> feedHolder.renderCommon(helper, item as FeedModel)
                TYPE_USER -> userHolder.renderHeader(helper, item as UserModel)
            }
        }
    }

}
