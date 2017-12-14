package cn.imrhj.cowlevel.network.interceptor

import cn.imrhj.cowlevel.manager.UserManager
import cn.imrhj.cowlevel.network.manager.COW_LEVEL_URI
import cn.imrhj.cowlevel.utils.StringUtils
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by rhj on 2017/11/29.
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        newRequest.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Mobile Safari/537.36")
        if (chain.request().url().host() == COW_LEVEL_URI?.host() && StringUtils.isNotBlank(UserManager.getUserModel().token)) {
            newRequest.addHeader("Cookie", "auth_token=${UserManager.getUserModel().token}")
        }

        return chain.proceed(newRequest.build())
    }
}