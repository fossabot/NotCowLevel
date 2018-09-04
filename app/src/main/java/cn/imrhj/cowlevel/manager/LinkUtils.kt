package cn.imrhj.cowlevel.manager

import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.ui.activity.LoginActivity

object LinkUtils {
    fun openLogin() {
        val activity = App.app.getLastActivity()
        if (activity !is LoginActivity) {
            SchemeUtils.startActivity(LoginActivity::class.java)
            activity.finish()
            activity.overridePendingTransition(0, 0)
        }
    }
}