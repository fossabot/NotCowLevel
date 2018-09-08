package cn.imrhj.cowlevel.ui.fragment.element

import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.ImageView
import cn.imrhj.cowlevel.R
import cn.imrhj.cowlevel.manager.SchemeUtils
import cn.imrhj.cowlevel.network.manager.RetrofitManager
import cn.imrhj.cowlevel.network.model.common.ListCountApiModel
import cn.imrhj.cowlevel.network.model.element.ArticleModel
import cn.imrhj.cowlevel.ui.activity.PersonActivity
import cn.imrhj.cowlevel.ui.base.ApiRecyclerFragment
import cn.imrhj.cowlevel.utils.cdnImageForDPSize
import cn.imrhj.cowlevel.utils.cdnImageForDPSquare
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observable

class ElementArticleFragment : ApiRecyclerFragment<ArticleModel, ListCountApiModel<ArticleModel>>() {
    var mId: Int = -1
    override fun getApiObservable(nextCursor: Int): Observable<ListCountApiModel<ArticleModel>> {
        return RetrofitManager.getInstance().elementArticle(mId, nextCursor)
    }

    override fun onNext(result: ListCountApiModel<ArticleModel>, isResetData: Boolean, nextCursor: Int) {
        updateList(result.list, isResetData)
        setHasMore(result.hasMore == 1)
        setNextCursor(nextCursor + 1)
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.item_aritcle
    }

    override fun convert(helper: BaseViewHolder, item: ArticleModel) {
        val avatar = helper.getView<ImageView>(R.id.avatar)
        Glide.with(this)
                .load(cdnImageForDPSquare(item.user?.avatar, 48))
                .apply(RequestOptions().circleCrop().placeholder(R.drawable.round_place_holder))
                .into(avatar)
        val picImageView = helper.getView<ImageView>(R.id.pic)
        picImageView.post {
            Glide.with(this)
                    .load(cdnImageForDPSize(item.pic, picImageView.width, picImageView.height))
                    .thumbnail(Glide.with(this).load(R.mipmap.anim_loading)
                            .apply(RequestOptions().centerInside()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions().centerCrop())
                    .into(picImageView)
        }
        helper.setText(R.id.name, item.user?.name)
                .setText(R.id.subtitle, "发布了文章")
                .setText(R.id.suffix, item.publishTimeBefore)
                .setText(R.id.title, item.title)
                .setText(R.id.tv_commit, "评论 ${item.commentCount}")
                .setText(R.id.tv_vote, "赞同 ${item.voteCount}")
                .setGone(R.id.btn_follower, false)
        // listener
        helper.getView<View>(R.id.user).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("avatar", item.user?.avatar)
            bundle.putString("name", item.user?.name)
            bundle.putString("url_slug", item.user?.url_slug)
            SchemeUtils.startActivityTransition(PersonActivity::class.java, bundle,
                    Pair.create(avatar as View, "avatar"))
        }
    }

    override fun getFirstPageIndex(): Int {
        return 1
    }
}