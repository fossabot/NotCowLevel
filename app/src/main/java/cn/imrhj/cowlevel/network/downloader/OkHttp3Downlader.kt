package cn.imrhj.cowlevel.network.downloader

import android.content.Context
import android.net.Uri
import android.os.StatFs
import cn.imrhj.cowlevel.App
import cn.imrhj.cowlevel.network.interceptor.ApiLogInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.picasso.Downloader
import com.squareup.picasso.Downloader.ResponseException
import com.squareup.picasso.NetworkPolicy
import okhttp3.*
import java.io.File
import java.io.IOException


/**
 * Created by rhj on 2017/12/6.
 */
class OkHttp3Downloader : Downloader {

    private val client: Call.Factory
    private val cache: Cache?

    constructor(context: Context) : this(defaultCacheDir(context)) {}

    constructor(context: Context, maxSize: Long) : this(defaultCacheDir(context), maxSize) {}

    @JvmOverloads constructor(cacheDir: File, maxSize: Long = calculateDiskCacheSize(cacheDir)) : this(createOkHttpClient(cacheDir, maxSize)) {}

    constructor(client: OkHttpClient) {
        this.client = client
        this.cache = client.cache()
    }

    constructor(client: Call.Factory) {
        this.client = client
        this.cache = null
    }

    @Throws(IOException::class)
    override fun load(uri: Uri, networkPolicy: Int): Downloader.Response {
        var cacheControl: CacheControl? = null
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                cacheControl = CacheControl.FORCE_CACHE
            } else {
                val builder = CacheControl.Builder()
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.noCache()
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.noStore()
                }
                cacheControl = builder.build()
            }
        }

        val builder = Request.Builder().url(uri.toString())
        if (cacheControl != null) {
            builder.cacheControl(cacheControl)
        }

        val response = client.newCall(builder.build()).execute()
        val responseCode = response.code()
        if (responseCode >= 300) {
            response.body()!!.close()
            throw ResponseException("$responseCode  ${response.message()}", networkPolicy,
                    responseCode)
        }

        val fromCache = response.cacheResponse() != null

        val responseBody = response.body()
        return Downloader.Response(responseBody!!.byteStream(), fromCache, responseBody.contentLength())
    }

    override fun shutdown() {
        if (cache != null) {
            try {
                cache.close()
            } catch (ignored: IOException) {
            }

        }
    }

    companion object {
        private val PICASSO_CACHE = "picasso-cache"
        private val MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024 // 5MB
        private val MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024 // 50MB

        private fun defaultCacheDir(context: Context): File {
            val cache = File(context.applicationContext.cacheDir, PICASSO_CACHE)
            if (!cache.exists()) {
                cache.mkdirs()
            }
            return cache
        }

        private fun calculateDiskCacheSize(dir: File): Long {
            var size = MIN_DISK_CACHE_SIZE.toLong()

            try {
                val statFs = StatFs(dir.absolutePath)
                val available = statFs.blockCountLong.toLong() * statFs.blockSizeLong
                size = available / 50
            } catch (ignored: IllegalArgumentException) {
            }

            return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE.toLong()), MIN_DISK_CACHE_SIZE.toLong())
        }

        fun createDefaultCache(context: Context): Cache {
            val dir = defaultCacheDir(context)
            return Cache(dir, calculateDiskCacheSize(dir))
        }

        private fun createOkHttpClient(cacheDir: File, maxSize: Long): OkHttpClient {
            return OkHttpClient.Builder()
                    .cache(Cache(cacheDir, maxSize))
                    .addInterceptor(ChuckInterceptor(App.getApplication().applicationContext))
                    .build()
        }
    }
}
