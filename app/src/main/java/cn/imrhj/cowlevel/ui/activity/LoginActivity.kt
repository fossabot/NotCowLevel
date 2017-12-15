package cn.imrhj.cowlevel.ui.activity

import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.manager.UserManager
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.utils.ConvertUtils
import cn.imrhj.cowlevel.utils.RegexUtils
import cn.imrhj.cowlevel.utils.ResourcesUtils
import cn.imrhj.cowlevel.utils.StringUtils
import io.reactivex.android.schedulers.AndroidSchedulers
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
        if (checkMail() && checkPassword()) {
            btnLogin.startAnimation()
            RetrofitManager.getInstance().login(etMail.text.toString(), etPassword.text.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ loginModel ->
                        if (StringUtils.isNotBlank(loginModel.authToken)) {
                            btnLogin.doneLoadingAnimation(Color.GREEN, ConvertUtils.drawable2Bitmap(ResourcesUtils.getDrawable(R.drawable.ic_done_white_48dp)!!))
                            UserManager.setToken(loginModel.authToken!!)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }, { error ->
                        etMail.error = error.message
                        etPassword.error = error.message
                        btnLogin.revertAnimation()
                    })


        }
    }

    private fun doRegister() {
        if (checkMail() && checkPassword() && checkInv()) {

        }
    }

    private fun checkMail(): Boolean {
        if (StringUtils.isBlank(etMail.text)) {
            etMail.error = "你还没有填写邮箱"
            return false
        }
        if (!RegexUtils.isMail(etMail.text)) {
            etMail.error = "邮箱格式不正确"
            return false
        }
        return true
    }

    private fun checkPassword(): Boolean {
        if (StringUtils.isBlank(etPassword.text)) {
            etPassword.error = "你还没有填写密码"
            return false
        }
        return true
    }

    private fun checkInv(): Boolean {
        if (StringUtils.isBlank(etInviteCode.text)) {
            etInviteCode.error = "邀请码不能为空"
            return false
        }
        return true
    }


    private fun changeView(status: Boolean) {
        etMail.error = null
        etPassword.error = null
        etInviteCode.error = null

        llRegister.isClickable = !status
        llLogin.isClickable = status
        tvRegister.setTextColor(ResourcesUtils.getColor(if (status) R.color.white else R.color.colorWhite5))
        tvLogin.setTextColor(ResourcesUtils.getColor(if (status) R.color.colorWhite5 else R.color.white))
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
                    .setToolbarColor(ResourcesUtils.getColor(R.color.colorPrimary))
                    .setSecondaryToolbarColor(ResourcesUtils.getColor(R.color.colorPrimaryDark))
                    .setCloseButtonIcon(ConvertUtils.drawable2Bitmap(ResourcesUtils.getDrawable(R.drawable.ic_arrow_back_white)!!))
                    .build()
                    .launchUrl(this, Uri.parse(url))
        } else {
            Log.d(Thread.currentThread().name, "class = LoginActivity rhjlog openUrl: todo")

        }
    }

}
