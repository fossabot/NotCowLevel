package cn.imrhj.cowlevel.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
    private val INVITE_URL = "https://cowlevel.net/reg"
    private val FORGOT_URL = "https://cowlevel.net/forgot"
//    private var isRegister = true

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        llRegister.setOnClickListener { _ ->
            this.hideKeyboard()
            Snackbar.make(llRegister, "请前往打开的网页注册新帐号", Snackbar.LENGTH_INDEFINITE)
                    .setAction("前往") { openUrl(INVITE_URL) }
                    .show()
        }

        llLogin.setOnClickListener {
            Snackbar.make(llLogin, "点我干啥？", Snackbar.LENGTH_SHORT).show()
        }

        btnLogin.setOnClickListener {
            doLogin()
        }

        tvForget.setOnClickListener {
            openUrl(FORGOT_URL)
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearError()
            }
        }

        etMail.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)

        // 添加actionDone按键回调
        etPassword.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(v.windowToken, 0)
                doLogin()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(etMail.windowToken, 0)
        }
    }

    private fun doLogin() {
        if (checkMail() && checkPassword()) {
            btnLogin.startAnimation()
            RetrofitManager.getInstance().login(etMail.text.toString(), etPassword.text.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ loginModel ->
                        if (StringUtils.isNotBlank(loginModel.authToken)) {
                            btnLogin.doneLoadingAnimation(Color.GREEN,
                                    ConvertUtils.drawable2Bitmap(ResourcesUtils.getDrawable(
                                            R.drawable.ic_done_white_48dp)!!))
                            UserManager.setToken(loginModel.authToken!!)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }, { error ->
                        tilMail.error = error.message
                        tilPassword.error = error.message
                        btnLogin.revertAnimation()
                    })


        }
    }

    private fun checkMail(): Boolean {
        if (StringUtils.isBlank(etMail.text)) {
            tilMail.error = "你还没有填写邮箱"
            return false
        }
        if (!RegexUtils.isMail(etMail.text)) {
            tilMail.error = "邮箱格式不正确"
            return false
        }
        return true
    }

    private fun checkPassword(): Boolean {
        if (StringUtils.isBlank(etPassword.text)) {
            tilPassword.error = "你还没有填写密码"
            return false
        }
        return true
    }

    private fun clearError() {
        if (tilMail.error != null) {
            tilMail.error = null
        }
        if (tilPassword.error != null) {
            tilPassword.error = null
        }
    }

//    private fun changeView(status: Boolean) {
//        tilMail.error = null
//        tilPassword.error = null
//
//        llRegister.isClickable = !status
//        llLogin.isClickable = status
//        tvRegister.setTextColor(ResourcesUtils.getColor(if (status) R.color.white else R.color.colorWhite5))
//        tvLogin.setTextColor(ResourcesUtils.getColor(if (status) R.color.colorWhite5 else R.color.white))
//        viewRegister.visibility = if (status) View.VISIBLE else View.GONE
//        viewLogin.visibility = if (status) View.GONE else View.VISIBLE
//        btnLogin.text = resources.getString(if (status) R.string.register else R.string.login)
//        etPassword.imeOptions = if (status) EditorInfo.IME_ACTION_NEXT else EditorInfo.IME_ACTION_DONE
//    }

    private fun openUrl(url: String) {
        SchemeUtils.openWithChromeTabs(url)
    }
}
