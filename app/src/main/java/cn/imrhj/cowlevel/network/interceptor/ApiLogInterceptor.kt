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

//        return if (BuildConfig.DEBUG) {
//            val response = chain.proceed(chain.request())
//            val body = response.body()
//            if (body != null) {
//                val bos = ByteArrayOutputStream()
//                bos.write(body.bytes())
//                XLog.b().w("api.resp:${chain.request().url()}, code:${response.code()},msg:${response.message()},\nbody:${bos.toString(Charsets.UTF_8.name())}")
//                val contentType = response.header("Content-Type")
//                response.newBuilder()
//                        .body(RealResponseBody(contentType, body.contentLength(), Okio.buffer(Okio.source(ByteArrayInputStream(bos.toByteArray())))))
//                        .build()
//            } else {
//                response
//            }
//        } else {
        return chain.proceed(chain.request())
//        }

    }
}