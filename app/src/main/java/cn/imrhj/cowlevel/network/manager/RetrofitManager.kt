package cn.imrhj.cowlevel.network.manager

import cn.imrhj.cowlevel.network.adapter.OuterUserAdapter
import cn.imrhj.cowlevel.network.exception.ApiException
import cn.imrhj.cowlevel.network.model.*
import cn.imrhj.cowlevel.network.service.CowLevel
import cn.imrhj.cowlevel.network.service.CowLevelRaw
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


    private val mCowLevel = mRetrofit.create(CowLevel::class.java)
    private val mCowLevelRaw = mRetrofit.create(CowLevelRaw::class.java)

    private fun <T : BaseModel> filterStatus(observable: Observable<ApiModel<T>>): Observable<T> {
        return observable.map {
            if (it.ec == 200) {
                return@map it.data
            } else {
                throw ApiException(it.em, it.ec)
            }
        }.subscribeOn(Schedulers.io())
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

    fun getFeedHome(): Observable<String> {
        return mCowLevelRaw.feedHomeRaw()
    }

    fun getElementFeed(id: Int, lastId: Int): Observable<FeedApiModel> {
        return filterStatus(mCowLevel.getElementFeed(id, lastId))
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