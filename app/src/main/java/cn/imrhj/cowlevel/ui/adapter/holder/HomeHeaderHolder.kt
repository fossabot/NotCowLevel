package cn.imrhj.cowlevel.ui.adapter.holder

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.manager.SchemeUtils
import cn.imrhj.cowlevel.network.model.FollowedPostNewModel
import cn.imrhj.cowlevel.network.model.FollowedTagNewModel
import cn.imrhj.cowlevel.network.model.NewContent
import cn.imrhj.cowlevel.ui.activity.ElementActivity
import cn.imrhj.cowlevel.utils.ScreenSizeUtil.dp2px
import cn.imrhj.cowlevel.utils.cdnImageForDPSize
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.chad.library.adapter.base.BaseViewHolder
import java.lang.ref.WeakReference

class HomeHeaderHolder() {
    constructor(fragment: Fragment) : this() {
        mFragment = WeakReference(fragment)
    }

    constructor(activity: Activity) : this() {
        mActivity = WeakReference(activity)
    }

    private var mFragment: WeakReference<Fragment>? = null
    private var mActivity: WeakReference<Activity>? = null

    private fun getActivity(): Activity {
        return mActivity?.get() ?: App.app.getLastActivity()
    }

    private fun getFragment(): Fragment? {
        return mFragment?.get()
    }

    private fun getGlide(): RequestManager {
        return if (mFragment?.get() != null) Glide.with(getFragment()!!) else Glide.with(getActivity())
    }

    fun renderBase(helper: BaseViewHolder) {

    }

    fun getSubtitle(newContent: NewContent?): String {
        val list = ArrayList<String>()
        if (newContent?.articleCount ?: 0 > 0) {
            list.add("新文章")
        }
        if (newContent?.questionCount ?: 0 > 0) {
            list.add("新问题")
        }
        if (newContent?.reviewCount ?: 0 > 0) {
            list.add("新评价")
        }
        if (newContent?.videoCount ?: 0 > 0) {
            list.add("新视频")
        }
        return list.reduce { str1, str2 -> "$str1,$str2" }
    }

    fun renderPost(helper: BaseViewHolder, posts: List<FollowedPostNewModel>) {
        val parent = helper.getView<LinearLayout>(R.id.container)
        helper.getView<HorizontalScrollView>(R.id.scrollView).scrollTo(0, 0)
        parent.removeAllViews()
        posts.forEach {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_header_post, parent, false)
            val title = view.findViewById<TextView>(R.id.title)
            val subTitle = view.findViewById<TextView>(R.id.subtitle)
            title.text = it.title
            subTitle.text = this.getSubtitle(it.newContent)
            getGlide().load(cdnImageForDPSize(it.pic, 160, 80))
                    .into(view.findViewById(R.id.cover))
            view.setOnClickListener { _ ->
                SchemeUtils.startActivity(ElementActivity::class.java)
            }

            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.rightMargin = dp2px(12)
            parent.addView(view, params)
        }
        helper.setText(R.id.title, "我关注的游戏")
        helper.getView<TextView>(R.id.more).setOnClickListener {
            // todo goto more
            Log.d(Thread.currentThread().name, "class = HomeHeaderHolder rhjlog renderPost: onClick")
        }
    }

    fun renderTag(helper: BaseViewHolder, tags: List<FollowedTagNewModel>) {
        val parent = helper.getView<LinearLayout>(R.id.container)
        helper.getView<HorizontalScrollView>(R.id.scrollView).scrollTo(0, 0)
        parent.removeAllViews()
        tags.forEach {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_header_tag, parent, false)
            val title = view.findViewById<TextView>(R.id.title)
            val subTitle = view.findViewById<TextView>(R.id.subtitle)
            title.text = it.name
            subTitle.text = this.getSubtitle(it.newContent)
            val cover = view.findViewById<ImageView>(R.id.cover)
            getGlide().load(cdnImageForDPSquare(it.pic, 80))
                    .into(cover)
            view.setOnClickListener { _ ->
                //                SchemeUtils.startActivity(ElementActivity::class.java)
                val bundle = Bundle()
                bundle.putString("cover", it.pic)
                bundle.putString("name", it.name)
                bundle.putString("id", it.id?.toString())
                SchemeUtils.startActivityTransition(ElementActivity::class.java, bundle,
                        Pair.create(cover as View, "cover")
                )
            }
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.rightMargin = dp2px(20)
            parent.addView(view, params)
        }
        helper.setText(R.id.title, "我关注的元素")
        helper.getView<TextView>(R.id.more).setOnClickListener {
            // todo goto more
            Log.d(Thread.currentThread().name, "class = HomeHeaderHolder rhjlog renderPost: onClick")
        }
    }
}