package cn.imrhj.cowlevel.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView

/**
 * Created by rhj on 16/12/2017.
 */
class CowWebView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : WebView(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)
}