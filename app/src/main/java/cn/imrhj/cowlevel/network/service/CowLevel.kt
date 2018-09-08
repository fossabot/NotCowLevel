package cn.imrhj.cowlevel.network.service

import cn.imrhj.cowlevel.network.model.ApiModel
import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.LoginModel
import cn.imrhj.cowlevel.network.model.OuterUserModel
import cn.imrhj.cowlevel.network.model.common.ListCountApiModel
import cn.imrhj.cowlevel.network.model.common.NotifyModel
import cn.imrhj.cowlevel.network.model.element.ArticleModel
import cn.imrhj.cowlevel.network.model.element.GameModel
import cn.imrhj.cowlevel.network.model.element.QuestionModel
import cn.imrhj.cowlevel.network.model.feed.FeedApiModel
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
     * 获取用户的时间线
     * @param name 用户url_slug
     * @return Observable
     */
    @GET("/people/{name}/timeline")
    fun getPeopleTimeline(@Path("name") name: String, @Query("last_id") id: Int = 0): Observable<ApiModel<FeedApiModel>>

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

    /**
     * 获取 element 的 feed
     * @param id element id
     * @param lastId 分页
     * @return Observable
     */
    @GET("/element/{id}/feed")
    fun getElementFeed(@Path("id") id: Int, @Query("last_id") lastId: Int = 0): Observable<ApiModel<FeedApiModel>>

    /**
     * 获取 element 的问题
     * @param tagId elementID
     * @param page 当前分页
     * @return Observable
     */
    @GET("/api/search/question?per_page=10&sort=update_time&sort_type=desc&log=1")
    fun getElementQuestion(@Query("tag_id") tagId: Int, @Query("page") page: Int)
            : Observable<ApiModel<ListCountApiModel<QuestionModel>>>

    /**
     * 获取 element 的文章
     * @param tagId elementID
     * @param page 当前分页
     * @return Observable
     */
    @GET("/api/search/article")
    fun getElementArticle(@Query("tag_id") tagId: Int, @Query("page") page: Int)
            : Observable<ApiModel<ListCountApiModel<ArticleModel>>>

    @GET("/api/search/game")
    fun getElementGame(@Query("tag_id") tagId: Int, @Query("page") page: Int)
            : Observable<ApiModel<ListCountApiModel<GameModel>>>

    /**
     * 检查是否有新的通知消息
     * @return Observable
     */
    @GET("/user/notify/check")
    fun checkNotify(): Observable<ApiModel<NotifyModel>>
}