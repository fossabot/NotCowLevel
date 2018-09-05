package cn.imrhj.cowlevel.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.webkit.*
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.manager.SchemeUtils
import cn.imrhj.cowlevel.manager.UserManager
import cn.imrhj.cowlevel.network.manager.COW_LEVEL_URI
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
        loading.setHandColor(ContextCompat.getColor(applicationContext, R.color.colorRedAccept))
        loading.startAnim()
//        setSupportActionBar(toolbar)
        url = intent.getStringExtra("url")
        //支持获取手势焦点，输入用户名、密码或其他
        webview.requestFocusFromTouch()

        webview.settings.javaScriptEnabled = true
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
        webview.settings.defaultTextEncodingName = "utf-8"
        webview.settings.loadsImagesAutomatically = true
        webview.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))

        webview.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d(Thread.currentThread().name, "class = WebviewActivity rhjlog onPageFinished: ")
            }

            override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?): Boolean {
                if (COW_LEVEL_URI?.host() == request?.url?.host) {
                    return super.shouldOverrideUrlLoading(view, request)
                }
                if (request?.url != null) {
                    SchemeUtils.openWithChromeTabs(request.url)
                }
                return true
            }
        }
        webview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Log.d(Thread.currentThread().name, "class = WebviewActivity rhjlog onProgressChanged: $newProgress")
                if (newProgress >= 80) {
                    loading.stopAnim()
                    loading.visibility = View.GONE

                }
            }
        }
        if (StringUtils.isNotBlank(UserManager.getUserModel().token)) {
            CookieManager.getInstance().setCookie(COW_LEVEL_URL,
                    "auth_token=${UserManager.getUserModel().token}")
        }
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
        webview.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        webview.onResume()
        webview.resumeTimers()
    }


    override fun onDestroy() {
        super.onDestroy()
        webview.visibility = View.GONE
        refresh.removeAllViews()
        webview.loadUrl("about:blank")
        webview.stopLoading()
        webview.webViewClient = null
        webview.webChromeClient = null
        webview.destroy()
    }
}
