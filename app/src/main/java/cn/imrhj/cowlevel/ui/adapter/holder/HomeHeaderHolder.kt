package cn.imrhj.cowlevel.ui.adapter.holder

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
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

    private fun renderParent(helper: BaseViewHolder): LinearLayout {
        val parent = helper.getView<LinearLayout>(R.id.container)
        helper.getView<HorizontalScrollView>(R.id.scrollView).scrollTo(0, 0)
        parent.removeAllViews()
        return parent
    }

    private fun getSubtitle(newContent: NewContent?): String {
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

    private fun renderOneTag(parent: LinearLayout, @LayoutRes resId: Int, title: String?,
                             newContent: NewContent?, imgUrl: String?,
                             listener: ((view: View) -> Unit)? = null) {
        val view = LayoutInflater.from(parent.context).inflate(resId, parent, false)
        val titleView = view.findViewById<TextView>(R.id.title)
        val subTitle = view.findViewById<TextView>(R.id.subtitle)
        titleView.text = title
        subTitle.text = this.getSubtitle(newContent)
        getGlide().load(imgUrl).into(view.findViewById(R.id.cover))
        view.setOnClickListener(listener)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        params.rightMargin = dp2px(12)
        parent.addView(view, params)
    }

    fun renderPost(helper: BaseViewHolder, posts: List<FollowedPostNewModel>) {

        val parent = renderParent(helper)
        posts.forEach {
            renderOneTag(parent, R.layout.item_home_header_post, it.title, it.newContent,
                    cdnImageForDPSize(it.pic, 160, 80))
        }
        helper.setText(R.id.title, "我关注的游戏")
        helper.getView<TextView>(R.id.more).setOnClickListener {
            // todo goto more
            Log.d(Thread.currentThread().name, "class = HomeHeaderHolder rhjlog renderPost: onClick")
        }
    }

    fun renderTag(helper: BaseViewHolder, tags: List<FollowedTagNewModel>) {
        val parent = renderParent(helper)
        tags.forEach {
            renderOneTag(parent, R.layout.item_home_header_tag, it.name, it.newContent,
                    cdnImageForDPSquare(it.pic, 80)) { view ->
                val bundle = Bundle()
                bundle.putString("cover", it.pic)
                bundle.putString("name", it.name)
                bundle.putString("id", it.id?.toString())
                SchemeUtils.startActivityTransition(ElementActivity::class.java, bundle,
                        Pair.create(view.findViewById(R.id.cover), "cover")
                )
            }
        }
        helper.setText(R.id.title, "我关注的元素")
        helper.getView<TextView>(R.id.more).setOnClickListener {
            // todo goto more
            Log.d(Thread.currentThread().name, "class = HomeHeaderHolder rhjlog renderPost: onClick")
        }
    }
}