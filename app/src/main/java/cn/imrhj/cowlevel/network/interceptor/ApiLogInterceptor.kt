package cn.imrhj.cowlevel.network.interceptor

import cn.imrhj.cowlevel.BuildConfig
import com.elvishew.xlog.XLog
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Created by rhj on 2017/12/4.
 */
class ApiLogInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val request = chain.request()
            val requestHeaders = request.headers()
            XLog.t().st(1).b().w(request)
            XLog.w(requestHeaders)
//            XLog.w(requestBody)
        } else {
            XLog.w("api.req url " + chain.request().url().toString() + ", METHOD " + chain.request().method())
        }

        return chain.proceed(chain.request())
    }
}