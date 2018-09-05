package cn.imrhj.cowlevel.ui.fragment.element

import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.consts.ItemTypeEnum
import cn.imrhj.cowlevel.consts.ItemTypeEnum.TYPE_ELEMENT_RELATED
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.element.ElementRelatedModel
import cn.imrhj.cowlevel.ui.adapter.FeedAdapter
import cn.imrhj.cowlevel.ui.base.RecyclerFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import io.reactivex.android.schedulers.AndroidSchedulers

class ElementFeedFragment : RecyclerFragment<BaseModel>() {
    var mId: Int = -1
    private var mRelatedModel: ElementRelatedModel? = null

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
                    helper.setText(R.id.tv_child, "子元素 (${item.child?.size ?: 0})")
                }
            }
        }
    }

    fun setRelatedData(item: ElementRelatedModel?) {
        mRelatedModel = item
        if (mFirstLoaded && item != null) {
            mAdapter.addData(0, item)
        }
    }

    override fun getAdapter(): BaseQuickAdapter<BaseModel, BaseViewHolder> {
        return mAdapter
    }

    override fun loadServer(isResetData: Boolean, nextCursor: Int) {
        RetrofitManager.getInstance().elementFeed(mId, nextCursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if (isResetData && mRelatedModel != null) {
                        val list = ArrayList<BaseModel>((result.list?.size ?: 0) + 1)
                        list.add(mRelatedModel!!)
                        if (result.list != null) {
                            list.addAll(result.list)
                        }
                        updateList(list, isResetData)
                    } else {
                        updateList(result.list, isResetData)
                    }
                    setHasMore(result.has_more == 1)
                    setNextCursor(nextCursor + 1)
                }, mOnError, mOnComplete)
    }

}