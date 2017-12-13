package cn.imrhj.cowlevel.ui.activity

import android.content.Intent
import android.os.Bundle
import cn.imrhj.cowlevel.ui.base.BaseActivity

class StartActivity : BaseActivity() {
    override fun layoutId(): Int? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
