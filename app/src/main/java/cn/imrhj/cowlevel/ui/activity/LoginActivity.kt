package cn.imrhj.cowlevel.ui.activity

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.utils.ConvertUtils
import cn.imrhj.cowlevel.utils.RegexUtil
import cn.imrhj.cowlevel.utils.ResourceUtil
import cn.imrhj.cowlevel.utils.StringUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private val INVITE_URL = "https://cowlevel.net/apply"
    private var customTabsReady = false
    private var isRegister = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindCustomTabsService()
    }

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        tvInvite.setOnClickListener { openUrl(INVITE_URL) }
        llRegister.setOnClickListener {
            if (!isRegister) {
                isRegister = true
                changeView(isRegister)
            }
        }

        llLogin.setOnClickListener {
            if (isRegister) {
                isRegister = false
                changeView(isRegister)
            }
        }

        btnLogin.setOnClickListener {
            if (isRegister) doRegister() else doLogin()
        }

    }

    private fun doLogin() {
        checkMail()

    }

    private fun doRegister() {
        checkMail()

    }

    private fun checkMail(): Boolean {
        if (StringUtil.isBlank(etMail.text)) {
            etMail.error = "邮箱不能为空"
            return false
        }
        if (!RegexUtil.isMail(etMail.text)) {
            etMail.error = "邮箱格式不正确"
            return false
        }
        return true
    }


    private fun changeView(status: Boolean) {
        llRegister.isClickable = !status
        llLogin.isClickable = status
        tvRegister.setTextColor(ResourceUtil.getColor(if (status) R.color.white else R.color.colorWhite5))
        tvLogin.setTextColor(ResourceUtil.getColor(if (status) R.color.colorWhite5 else R.color.white))
        viewRegister.visibility = if (status) View.VISIBLE else View.GONE
        viewLogin.visibility = if (status) View.GONE else View.VISIBLE
        tilInviteCode.visibility = if (status) View.VISIBLE else View.GONE
        btnLogin.text = resources.getString(if (status) R.string.register else R.string.login)
        etPassword.imeOptions = if (status) EditorInfo.IME_ACTION_NEXT else EditorInfo.IME_ACTION_DONE
    }


    private fun bindCustomTabsService() {
        CustomTabsClient.bindCustomTabsService(applicationContext, "com.android.chrome",
                object : CustomTabsServiceConnection() {
                    override fun onCustomTabsServiceConnected(name: ComponentName?,
                                                              client: CustomTabsClient?) {
                        customTabsReady = true
                    }

                    override fun onServiceDisconnected(name: ComponentName?) {
                    }
                })

    }

    private fun openUrl(url: String) {
        if (customTabsReady) {
            CustomTabsIntent.Builder()
                    .setToolbarColor(ResourceUtil.getColor(R.color.colorPrimary))
                    .setSecondaryToolbarColor(ResourceUtil.getColor(R.color.colorPrimaryDark))
                    .setCloseButtonIcon(ConvertUtils.drawable2Bitmap(ResourceUtil.getDrawable(R.drawable.ic_arrow_back_white)!!))
                    .build()
                    .launchUrl(this, Uri.parse(url))
        } else {

        }
    }

}
