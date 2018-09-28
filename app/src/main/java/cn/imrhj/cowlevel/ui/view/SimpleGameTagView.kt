package cn.imrhj.cowlevel.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.network.model.common.TagModel

@SuppressLint("ViewConstructor")
class SimpleGameTagView(context: Context, tag: TagModel) : FrameLayout(context) {
    private val mTag = tag

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_game_tag, this, false)
        val textView = view.findViewById<TextView>(R.id.tv_tag)
        textView.text = mTag.name
        addView(view)
    }
}