package cn.imrhj.cowlevel.network.service

import cn.imrhj.cowlevel.network.model.*
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

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST("/api/passport/login")
    fun login(@Field("email") email: String, @Field("password") password: String): Observable<ApiModel<LoginModel>>

    /**
     * 注册（太麻烦了,暂时不做）
     */
    @FormUrlEncoded
    @POST("/passport/signup/send-verify-code")
    fun register(@Field("email") email: String, @Field("password") password: String): Observable<ApiModel<LoginModel>>

    /**
     * 获取用户概要数据（其实是能拿到的最详细的了（ ＴДＴ））
     * @param name 用户url_slug
     * @return Observable
     */
    @GET("/people/{name}/card")
    fun getPeople(@Path("name") name: String): Observable<ApiModel<OuterUserModel>>

    /**
     * 点赞评价
     * @param id 评价id
     * @return Observable
     */
    @FormUrlEncoded
    @POST("/api/review/vote")
    fun voteReview(@Field("id") id: Int): Observable<ApiModel<BaseModel>>

    /**
     * 取消点赞评价
     * @param id 评价id
     * @return Observable
     */
    @FormUrlEncoded
    @POST("/api/review/vote")
    fun unvoteReview(@Field("id") id: Int): Observable<ApiModel<BaseModel>>
}