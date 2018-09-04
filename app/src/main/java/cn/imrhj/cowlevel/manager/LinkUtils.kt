package cn.imrhj.cowlevel.manager

import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.ui.activity.LoginActivity

object LinkUtils {
    fun openLogin() {
        val activity = App.app.getLastActivity()
        SchemeUtils.startActivity(LoginActivity::class.java)
        if (activity !is LoginActivity) {
            activity.finish()
            activity.overridePendingTransition(0, 0)
        }
    }
}