package cn.imrhj.cowlevel.manager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
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
            App.app.startActivity(intent)
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

        fun <T> startActivity(clazz: Class<T>, bundle: Bundle) {
            val intent = Intent(App.app.getLastActivity(), clazz)

            App.app.startActivity(intent)
        }
    }
}