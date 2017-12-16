package cn.imrhj.cowlevel.ui.activity

import android.annotation.SuppressLint
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.manager.UserManager
import cn.imrhj.cowlevel.network.manager.COW_LEVEL_URL
import cn.imrhj.cowlevel.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity : BaseActivity() {
    private var url: String? = null
    override fun layoutId(): Int? {
        return R.layout.activity_webview
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
//        setSupportActionBar(toolbar)
        url = intent.getStringExtra("url")
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
            }
        }
        webview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
            }
        }
        CookieManager.getInstance().setCookie(COW_LEVEL_URL, "auth_token=${UserManager.getUserModel().token}")
        webview.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        webview.onPause()
    }

    override fun onResume() {
        super.onResume()
        webview.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        webview.visibility = View.GONE
        refresh.removeAllViews()
        webview.destroy()
    }
}
