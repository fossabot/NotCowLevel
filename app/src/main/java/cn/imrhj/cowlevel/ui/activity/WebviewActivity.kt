package cn.imrhj.cowlevel.ui.activity

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.manager.UserManager
import cn.imrhj.cowlevel.network.manager.COW_LEVEL_URL
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.utils.StringUtils
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity : BaseActivity() {
    private var url: String? = null
    override fun layoutId(): Int? {
        return R.layout.activity_webview
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        url = intent.getStringExtra("url")
        refresh.setOnRefreshListener { webview.loadUrl(url) }
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                refresh.isRefreshing = false
            }
        }
        webview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
            }
        }
        CookieManager.getInstance().setCookie(COW_LEVEL_URL, "auth_token=${UserManager.getUserModel().token}")
        Log.d(Thread.currentThread().name, "class = WebviewActivity rhjlog initView: " + CookieManager.getInstance().getCookie(COW_LEVEL_URL))
        webview.loadUrl(url)
    }
}
