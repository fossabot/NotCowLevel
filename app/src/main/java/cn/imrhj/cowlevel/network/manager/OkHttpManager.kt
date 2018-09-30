package cn.imrhj.cowlevel.network.manager

import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.network.interceptor.ApiLogInterceptor
import cn.imrhj.cowlevel.network.interceptor.HeaderInterceptor
import com.elvishew.xlog.XLog
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.lang.Exception
import java.util.concurrent.TimeUnit

object OkHttpManager {
    private val mClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(ChuckInterceptor(App.app.applicationContext))
                .addInterceptor(ApiLogInterceptor())
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
    }

    fun getClient(): OkHttpClient {
        return mClient
    }

    fun getServerData(url: String): Observable<String?> {
        return Observable.create<Response> {
            try {
                val response = mClient.newCall(Request.Builder().url(url).build()).execute()
                it.onNext(response)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
                .map { it.body()?.string() }
                .subscribeOn(Schedulers.io())
//        return Observable.just(url)
//                .map {
//                    try {
//                        return@map mClient.newCall(Request.Builder().url(url).build()).execute().body()?.string()
//                    } catch (e: Exception) {
//                        XLog.b().st(3).e("try catch OkHttpManager getServerData:$e")
//                        return@map ""
//                    }
//                }
//                .subscribeOn(Schedulers.io())
    }
}