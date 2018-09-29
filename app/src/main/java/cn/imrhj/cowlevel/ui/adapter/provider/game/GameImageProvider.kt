package cn.imrhj.cowlevel.ui.adapter.provider.game

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.network.model.game.FeatureImageListModel
import cn.imrhj.cowlevel.utils.ScreenSizeUtil.dp2px
import cn.imrhj.cowlevel.utils.cdnImageForSize
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider

class GameImageProvider : BaseItemProvider<FeatureImageListModel, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_game_images
    }

    override fun viewType(): Int {
        return ItemTypeEnum.TYPE_GAME_IMAGE.ordinal
    }

    override fun convert(helper: BaseViewHolder, data: FeatureImageListModel, position: Int) {
        helper.setText(R.id.tv_title, "相关图片 （${data.photoCount}）")
        val parent = helper.getView<LinearLayout>(R.id.ll_image)
        data.list?.forEach {
            val image = ImageView(parent.context)
            val height = dp2px(170)
            Glide.with(parent)
                    .load(cdnImageForSize(it.pic, 0, height))
                    .into(image)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height)
            params.marginEnd = dp2px(8)
            parent.addView(image, params)
        }
        val moreView = LayoutInflater.from(parent.context).inflate(R.layout.item_image_more, parent, false)
        moreView.setOnClickListener {
            //todo 跳转到更多图片页面
        }
        parent.addView(moreView)
    }
}
