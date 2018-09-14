package cn.imrhj.cowlevel.manager

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.util.Pair
import android.view.View
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.ui.activity.WebviewActivity
import cn.imrhj.cowlevel.utils.ConvertUtils
import cn.imrhj.cowlevel.utils.ResourcesUtils

/**
 * Created by rhj on 15/12/2017.
 */
class SchemeUtils {
    companion object {
        fun openLink(url: String) {
            val intent = Intent(App.app.getLastActivity(), WebviewActivity::class.java)
            intent.putExtra("url", url)
            App.app.getLastActivity().startActivity(intent)
        }

        fun openWithChromeTabs(url: String) {
            openWithChromeTabs(Uri.parse(url))
        }

        fun openWithChromeTabs(uri: Uri) {
            val tabsIntent = CustomTabsIntent.Builder()
                    .setToolbarColor(ResourcesUtils.getColor(R.color.colorPrimary))
                    .setSecondaryToolbarColor(ResourcesUtils.getColor(R.color.colorPrimaryDark))
                    .setCloseButtonIcon(ConvertUtils.drawable2Bitmap(ResourcesUtils.getDrawable(R.drawable.ic_arrow_back_white)!!))
                    .build()

            tabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK


            tabsIntent.launchUrl(App.app, uri)
        }

        fun <T> startActivity(clazz: Class<T>, bundle: Bundle? = null) {
            val intent = Intent(App.app.getLastActivity(), clazz)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            App.app.getLastActivity().startActivity(intent)
        }

        fun <T> presentActivity(clazz: Class<T>, bundle: Bundle? = null) {
            val activity = App.app.getLastActivity()
            val intent = Intent(activity, clazz)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            activity.startActivity(intent)
        }

        fun <T> startActivityTransition(clazz: Class<T>, bundle: Bundle, vararg sharedElements: Pair<View?, String>) {
            val activity = App.app.getLastActivity()
            val intent = Intent(activity, clazz)
            intent.putExtras(bundle)
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, *sharedElements)
            activity.startActivity(intent, options.toBundle())
            activity.overridePendingTransition(0, 0)
        }

    }
}