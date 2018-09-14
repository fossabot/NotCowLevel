package cn.imrhj.cowlevel.ui.fragment.element.filter

import android.app.Dialog
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import cn.imrhj.cowlevel.R
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment

class GameElementFilterFabFragment : AAH_FabulousFragment() {
    override fun setupDialog(dialog: Dialog?, style: Int) {
        val mainContentView = View.inflate(context, R.layout.fragment_filter_game, null)
        val contentView = mainContentView.findViewById<RelativeLayout>(R.id.rl_content)
        val buttonsLayout = mainContentView.findViewById<LinearLayout>(R.id.ll_buttons)

        setCallbacks(activity as Callbacks)
        setViewgroupStatic(buttonsLayout)
        setViewMain(contentView)
        setMainContentView(mainContentView)
        super.setupDialog(dialog, style)
    }
}