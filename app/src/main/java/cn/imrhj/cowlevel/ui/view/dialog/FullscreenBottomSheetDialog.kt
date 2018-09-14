package cn.imrhj.cowlevel.ui.view.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.ViewGroup
import cn.imrhj.cowlevel.utils.ScreenSizeUtil

class FullscreenBottomSheetDialog : BottomSheetDialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, theme: Int) : super(context, theme)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}