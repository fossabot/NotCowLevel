package cn.imrhj.cowlevel.network.manager

import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.network.interceptor.ApiLogInterceptor
import cn.imrhj.cowlevel.network.interceptor.HeaderInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request

object OkHttpManager {
    private val mClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(ChuckInterceptor(App.app.applicationContext))
                .addInterceptor(ApiLogInterceptor())
                .build()
    }

    fun getClient(): OkHttpClient {
        return mClient
    }

    fun getServerData(url: String): Observable<String?> {
        return Observable.just(url)
                .map { mClient.newCall(Request.Builder().url(it).build()).execute() }
                .map { it.body()?.string() }
                .subscribeOn(Schedulers.io())
    }
}