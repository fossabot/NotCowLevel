package cn.imrhj.cowlevel.ui.activity

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.inputmethod.EditorInfo
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.manager.SchemeUtils
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
    private var isRegister = true

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
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
        if (checkMail() && checkPassword()) {

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

    private fun changeView(status: Boolean) {
        etMail.error = null
        etPassword.error = null

        llRegister.isClickable = !status
        llLogin.isClickable = status
        tvRegister.setTextColor(ResourcesUtils.getColor(if (status) R.color.white else R.color.colorWhite5))
        tvLogin.setTextColor(ResourcesUtils.getColor(if (status) R.color.colorWhite5 else R.color.white))
        viewRegister.visibility = if (status) View.VISIBLE else View.GONE
        viewLogin.visibility = if (status) View.GONE else View.VISIBLE
        btnLogin.text = resources.getString(if (status) R.string.register else R.string.login)
        etPassword.imeOptions = if (status) EditorInfo.IME_ACTION_NEXT else EditorInfo.IME_ACTION_DONE
    }


//    private fun bindCustomTabsService() {
//        CustomTabsClient.bindCustomTabsService(applicationContext, "com.android.chrome",
//                object : CustomTabsServiceConnection() {
//                    override fun onCustomTabsServiceConnected(name: ComponentName?,
//                                                              client: CustomTabsClient?) {
//                        customTabsReady = true
//                    }
//
//                    override fun onServiceDisconnected(name: ComponentName?) {
//                    }
//                })
//
//    }

    private fun openUrl(url: String) {
        SchemeUtils.openWithChromeTabs(url)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
