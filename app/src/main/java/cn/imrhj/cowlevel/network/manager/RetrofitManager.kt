package cn.imrhj.cowlevel.network.manager

import android.widget.Toast
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.manager.UserManager
import cn.imrhj.cowlevel.network.adapter.OuterUserAdapter
import cn.imrhj.cowlevel.network.exception.ApiException
import cn.imrhj.cowlevel.network.model.*
import cn.imrhj.cowlevel.network.model.common.NotifyModel
import cn.imrhj.cowlevel.network.model.feed.FeedApiModel
import cn.imrhj.cowlevel.network.service.CowLevel
import com.google.gson.GsonBuilder
import com.google.gson.stream.MalformedJsonException
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络请求管理类
 * Created by rhj on 2017/11/29.
 */
const val COW_LEVEL_URL = "https://cowlevel.net/"
val COW_LEVEL_URI = HttpUrl.parse(COW_LEVEL_URL)

class RetrofitManager private constructor() {
    private val mGson = GsonBuilder()
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .registerTypeAdapter(OuterUserModel::class.java, OuterUserAdapter())
            .create()

    private val mRetrofit = Retrofit.Builder()
            .baseUrl(COW_LEVEL_URL)
            .client(OkHttpManager.getClient())
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


    private val mCowLevel = mRetrofit.create(CowLevel::class.java)

    private fun <T : BaseModel> filterStatus(observable: Observable<ApiModel<T>>): Observable<T> {
        return observable
                .map {
                    if (it.ec == 200) {
                        return@map it.data
                    } else {
                        throw ApiException(it.em, it.ec)
                    }
                }
                .subscribeOn(Schedulers.io())
                .doOnError {
                    if (it is MalformedJsonException) {
                        Toast.makeText(App.app.getLastActivity(), "认证失败,请重新登录", Toast.LENGTH_LONG).show()
                        UserManager.logout()
                    }
                }
    }

    fun feedTimeline(id: Int = 0): Observable<FeedApiModel> {
        return filterStatus(mCowLevel.feedTimeline(id))
    }

    fun hotFeeds(page: Int): Observable<FeedApiModel> {
        return filterStatus(mCowLevel.hotFeeds(page))
    }

    fun login(email: String, password: String): Observable<LoginModel> {
        return filterStatus(mCowLevel.login(email, password))
    }

    fun getUser(name: String): Observable<UserModel> {
        return filterStatus(mCowLevel.getPeople(name))
                .map { it.user }
    }

    fun getUserTimeLine(name: String, id: Int = 0): Observable<FeedApiModel> {
        return filterStatus(mCowLevel.getPeopleTimeline(name, id))
    }

    fun voteReview(id: Int): Observable<BaseModel> {
        return filterStatus(mCowLevel.voteReview(id))
    }

    fun unvoteReview(id: Int): Observable<BaseModel> {
        return filterStatus(mCowLevel.voteReview(id))
    }

    fun getElementFeed(id: Int, lastId: Int): Observable<FeedApiModel> {
        return filterStatus(mCowLevel.getElementFeed(id, lastId))
    }

    fun checkNotify(): Observable<NotifyModel> {
        return filterStatus(mCowLevel.checkNotify())
    }

    // 单例实现
    companion object {
        fun getInstance(): RetrofitManager {
            return Inner.mSingleInstance
        }
    }

    private object Inner {
        val mSingleInstance = RetrofitManager()
    }
}