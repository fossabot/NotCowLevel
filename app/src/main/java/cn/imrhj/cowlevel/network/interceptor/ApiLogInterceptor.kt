package cn.imrhj.cowlevel.network.interceptor

import cn.imrhj.cowlevel.BuildConfig
import cn.imrhj.cowlevel.extensions.toLogString
import com.elvishew.xlog.XLog
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.RealResponseBody
import okio.Okio
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


/**
 * Created by rhj on 2017/12/4.
 */
class ApiLogInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (BuildConfig.DEBUG) {
            XLog.t().b().nst().tag("rhjlog req:").w(request.toLogString())
        } else {
            XLog.w("api.req:url=${request.url()}, METHOD=${request.method()}, HEADERS=${request.headers()}, body=${request.body()}")
        }

        return if (BuildConfig.DEBUG) {
            val response = chain.proceed(chain.request())
            XLog.t().b().nst().tag("rhjlog resp:").w("api.resp:${chain.request().url()}, " +
                    "code:${response.code()},msg:${response.message()}," +
                    "protocol:${response.protocol()}")
            val body = response.body()
            if (body != null) {
                val bos = ByteArrayOutputStream()
                bos.write(body.bytes())
                val bodyString = bos.toString(Charsets.UTF_8.name())
                if (bodyString.length < 2000) {
                    XLog.b().json(bodyString)
                }
                val contentType = response.header("Content-Type")
                response.newBuilder()
                        .body(RealResponseBody(contentType, body.contentLength(), Okio.buffer(Okio.source(ByteArrayInputStream(bos.toByteArray())))))
                        .build()
            } else {
                response
            }
        } else {
            return chain.proceed(chain.request())
        }

    }
}