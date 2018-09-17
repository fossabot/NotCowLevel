package cn.imrhj.cowlevel.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.imrhj.cowlevel.deeplink.AppDeepLinkModule
import cn.imrhj.cowlevel.deeplink.AppDeepLinkModuleLoader
import cn.imrhj.cowlevel.manager.UserManager
import com.airbnb.deeplinkdispatch.DeepLinkHandler

@DeepLinkHandler(AppDeepLinkModule::class)
class DeepLinkActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (UserManager.isLogin()) {
            val deepLinkDelegate = DeepLinkDelegate(AppDeepLinkModuleLoader())
            deepLinkDelegate.dispatchFrom(this)
        } else {
            val startIntent = Intent(this, LoginActivity::class.java)
            startIntent.data = intent.data
            startActivity(startIntent)
        }
        finish()
    }
}