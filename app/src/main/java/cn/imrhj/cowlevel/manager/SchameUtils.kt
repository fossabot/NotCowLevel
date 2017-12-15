package cn.imrhj.cowlevel.manager

import android.content.Intent
import android.os.Bundle
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.ui.activity.WebviewActivity

/**
 * Created by rhj on 15/12/2017.
 */
class SchameUtils {
    companion object {
        fun openLink(url: String) {
            val intent = Intent(App.getApplication().lastResumeActivity, WebviewActivity::class.java)
            intent.putExtra("url", url)
            App.getAppContext().startActivity(intent)
        }

        fun <T> startActivity(clazz: Class<T>, bundle: Bundle) {
            val intent = Intent(App.getApplication().lastResumeActivity, clazz)

            App.getAppContext().startActivity(intent)
        }
    }
}