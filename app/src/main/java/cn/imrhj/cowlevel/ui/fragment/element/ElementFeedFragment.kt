package cn.imrhj.cowlevel.ui.fragment.element

import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_ELEMENT_RELATED
import cn.imrhj.cowlevel.extensions.getColor
import cn.imrhj.cowlevel.extensions.parseHtml
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.element.ElementChildItemModel
import cn.imrhj.cowlevel.network.model.element.ElementRelatedModel
import cn.imrhj.cowlevel.ui.activity.ElementActivity
import cn.imrhj.cowlevel.ui.adapter.FeedAdapter
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import cn.imrhj.cowlevel.ui.view.recycler.LinearDividerItemDecoration
import cn.imrhj.cowlevel.utils.ScreenSizeUtil
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import io.reactivex.android.schedulers.AndroidSchedulers

class ElementFeedFragment : RecyclerFragment<BaseModel>() {
    var mId: Int = -1
    private var mRelatedModel: ElementRelatedModel? = null
    private val mContent by lazy { context ?: App.app.getLastActivity() }
    private val mDialog by lazy { BottomSheetDialog(mContent) }
    private val mDialogAdapter by lazy {
        object : BaseQuickAdapter<ElementChildItemModel, BaseViewHolder>(R.layout.item_related) {
            override fun convert(helper: BaseViewHolder?, item: ElementChildItemModel?) {
                val avatar = helper?.getView<ImageView>(R.id.iv_avatar)
                if (avatar != null) {
                    Glide.with(this@ElementFeedFragment)
                            .load(cdnImageForDPSquare(item?.pic, 52))
                            .into(avatar)
                }
                helper?.setText(R.id.tv_title, item?.name)
                        ?.setText(R.id.tv_subtitle, "${item?.followerCount}人关注")
                        ?.setText(R.id.tv_desc, item?.content?.parseHtml())
                        ?.setText(R.id.tv_game_number, "游戏 ${item?.postCount}")
                        ?.setText(R.id.tv_question_number, "问题 ${item?.questionCount}")
                        ?.setText(R.id.tv_article_number, "文章 ${item?.articleCount}")
                        ?.getView<ViewGroup>(R.id.ir_container)
                        ?.setOnClickListener {
                            ElementActivity.startWithShareElement(avatar as View, item?.pic,
                                    item?.name, item?.id, 52)
                        }
            }
        }
    }

    private lateinit var mToolbar: Toolbar

    private val mAdapter by lazy {
        object : FeedAdapter(ArrayList(), this) {
            override fun initType(multiTypeDelegate: MultiTypeDelegate<BaseModel>) {
                super.initType(multiTypeDelegate)
                multiTypeDelegate.registerItemType(TYPE_ELEMENT_RELATED.ordinal, R.layout.item_element_related)
            }

            override fun extendConvert(helper: BaseViewHolder, item: BaseModel?, type: ItemTypeEnum) {
                if (type == TYPE_ELEMENT_RELATED && item is ElementRelatedModel) {
                    helper.itemView.layoutParams
                    helper.setText(R.id.tv_parent, "父元素 (${item.parent?.size ?: 0})")
                            .setText(R.id.tv_child, "子元素 (${item.child?.size ?: 0})")
                            .addOnClickListener(R.id.ll_parent)
                            .addOnClickListener(R.id.ll_child)

                    setOnItemChildClickListener { _, view, _ ->
                        when (view.id) {
                            R.id.ll_child -> {
                                if (mRelatedModel?.child?.size ?: 0 > 0) {
                                    mDialogAdapter.setNewData(mRelatedModel?.child)
                                    mToolbar.title = "子元素"
                                    mDialog.show()
                                }
                            }
                            R.id.ll_parent -> {
                                if (mRelatedModel?.parent?.size ?: 0 > 0) {
                                    mDialogAdapter.setNewData(mRelatedModel?.parent)
                                    mToolbar.title = "父元素"
                                    mDialog.show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun setRelatedData(item: ElementRelatedModel?) {
        if (item == null || (item.child?.size ?: 0 == 0 && item.parent?.size ?: 0 == 0)) {
            return
        }
        mRelatedModel = item
        if (mFirstLoaded) {
            mAdapter.addData(0, item)
        }
        this.initBottomSheetDialog()
    }

    private fun initBottomSheetDialog() {
        val content = LayoutInflater.from(mContent).inflate(R.layout.dialog_related_element, null)
        val recyclerView = content.findViewById<RecyclerView>(R.id.recycler)
        mToolbar = content.findViewById(R.id.toolbar)
        mToolbar.setNavigationOnClickListener { mDialog.dismiss() }
        recyclerView.layoutManager = LinearLayoutManager(mContent)
        recyclerView.addItemDecoration(
                LinearDividerItemDecoration(recyclerView.context,
                        LinearLayoutManager.VERTICAL, R.drawable.background_divider,
                        true, false)

        )
        recyclerView.adapter = mDialogAdapter
        mDialog.setContentView(content)
        mDialog.setCancelable(true)
        mDialog.setCanceledOnTouchOutside(true)
        val parent = mDialog.window.findViewById<ViewGroup>(android.support.design.R.id.design_bottom_sheet)
        parent.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        parent.setBackgroundColor(getColor(R.color.colorPrimaryDark))

        val behavior = BottomSheetBehavior.from(parent)
        behavior.peekHeight = ScreenSizeUtil.getScreenHeight()
    }

    override fun getAdapter(): BaseQuickAdapter<BaseModel, BaseViewHolder> {
        return mAdapter
    }

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        RetrofitManager.getInstance().elementFeed(mId, nextCursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver {
                    if (isResetData && mRelatedModel != null) {
                        val list = ArrayList<BaseModel>((it.list?.size ?: 0) + 1)
                        list.add(mRelatedModel!!)
                        if (it.list != null) {
                            list.addAll(it.list)
                        }
                        updateList(list, isResetData)
                    } else {
                        updateList(it.list, isResetData)
                    }
                    setHasMore(it.has_more == 1)
                    setNextCursor(nextCursor + 1)
                })
    }

}