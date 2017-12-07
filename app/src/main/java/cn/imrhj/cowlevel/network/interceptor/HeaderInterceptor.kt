package cn.imrhj.cowlevel.network.interceptor

import cn.imrhj.cowlevel.BuildConfig
import cn.imrhj.cowlevel.network.manager.COW_LEVEL_URI
import cn.imrhj.cowlevel.network.manager.COW_LEVEL_URL
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by rhj on 2017/11/29.
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        if (chain.request().url().host() == COW_LEVEL_URI?.host()) {
            newRequest.addHeader("Cookie", "auth_token=${BuildConfig.COW_AUTH_TOKEN}")
                    .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Mobile Safari/537.36")
        }

        return chain.proceed(newRequest.build())
    }
}