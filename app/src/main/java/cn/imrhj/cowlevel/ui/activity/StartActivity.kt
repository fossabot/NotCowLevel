package cn.imrhj.cowlevel.ui.activity

import android.content.Intent
import android.os.Bundle
import cn.imrhj.cowlevel.manager.UserManager
import cn.imrhj.cowlevel.ui.base.BaseActivity
import cn.imrhj.cowlevel.utils.StringUtils

class StartActivity : BaseActivity() {
    override fun layoutId(): Int? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UserManager.isLogin()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}
