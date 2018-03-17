package cn.imrhj.cowlevel.network.manager

import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.network.exception.ApiException
import cn.imrhj.cowlevel.network.interceptor.ApiLogInterceptor
import cn.imrhj.cowlevel.network.interceptor.HeaderInterceptor
import cn.imrhj.cowlevel.network.model.*
import cn.imrhj.cowlevel.network.service.CowLevel
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络请求管理类
 * Created by rhj on 2017/11/29.
 */
val COW_LEVEL_URL = "https://cowlevel.net/"
val COW_LEVEL_URI = HttpUrl.parse(COW_LEVEL_URL)

class RetrofitManager private constructor() {
    private val mClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(ChuckInterceptor(App.app.applicationContext))
            .addInterceptor(ApiLogInterceptor())
            .build()

    private val mRetrofit = Retrofit.Builder()
            .baseUrl(COW_LEVEL_URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private val mCowLevel = mRetrofit.create(CowLevel::class.java)

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

    fun voteReview(id: Int): Observable<BaseModel> {
        return filterStatus(mCowLevel.voteReview(id))
    }

    fun unvoteReview(id: Int): Observable<BaseModel> {
        return filterStatus(mCowLevel.voteReview(id))
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