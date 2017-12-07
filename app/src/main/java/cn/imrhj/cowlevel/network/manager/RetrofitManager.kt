package cn.imrhj.cowlevel.network.manager

import android.net.Uri
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.network.interceptor.ApiLogInterceptor
import cn.imrhj.cowlevel.network.interceptor.HeaderInterceptor
import cn.imrhj.cowlevel.network.service.CowLevel
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by rhj on 2017/11/29.
 */
val COW_LEVEL_URL = "https://cowlevel.net/"
val COW_LEVEL_URI = HttpUrl.parse(COW_LEVEL_URL)
class RetrofitManager private constructor(){
    private val mClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(ChuckInterceptor(App.getApplication().applicationContext))
            .addInterceptor(ApiLogInterceptor())
            .build()

    private val mRetrofit = Retrofit.Builder()
            .baseUrl(COW_LEVEL_URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private val mCowLevel = mRetrofit.create(CowLevel::class.java)

    fun request() : CowLevel {
        return mCowLevel
    }


    // 单例实现
    companion object {
        fun getInstance() : RetrofitManager {
            return Inner.mSingleInstance
        }
    }

    private object Inner {
        val mSingleInstance = RetrofitManager()
    }
}