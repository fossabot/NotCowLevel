package cn.imrhj.cowlevel.network.service

import io.reactivex.Observable
import retrofit2.http.GET

interface CowLevelRaw {
    /**
     * 获取首页 html 源码
     */
    @GET("feed")
    fun feedHomeRaw(): Observable<String>
}