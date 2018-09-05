package cn.imrhj.cowlevel.utils

import cn.imrhj.cowlevel.App
import java.io.File

object CacheUtils {


    val externalCacheDir = App.app.externalCacheDir
    val externalCacheDirPath = externalCacheDir.absolutePath
    val logFilePath = externalCacheDirPath + File.separator + "log"
    fun getLogFileDir(): File {
        val file = File(externalCacheDir, "log")
        if (!file.exists()) {
            file.mkdir()
        }
        return file
    }
}