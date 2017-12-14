package cn.imrhj.cowlevel.network.service

import cn.imrhj.cowlevel.network.model.ApiModel
import cn.imrhj.cowlevel.network.model.FeedApiModel
import cn.imrhj.cowlevel.network.model.LoginModel
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by rhj on 2017/12/4.
 */
interface CowLevel {

    /**
     * 获取feed时间线
     */
    @GET("/api/feed/timeline")
    fun feedTimeline(@Query("last_id") id: Int = 0): Observable<ApiModel<FeedApiModel>>

    /**
     * 获取feed时间线
     */
    @GET("/discover/hot-feeds")
    fun hotFeeds(@Query("page") id: Int = 0): Observable<ApiModel<FeedApiModel>>

    @FormUrlEncoded
    @POST("/api/passport/login")
    fun login(@Field("email") email: String, @Field("password") password: String): Observable<ApiModel<LoginModel>>
}