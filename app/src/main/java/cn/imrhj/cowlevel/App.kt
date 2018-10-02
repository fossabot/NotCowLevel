package cn.imrhj.cowlevel

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cn.imrhj.cowlevel.log.AppRecoveryCallback
import cn.imrhj.cowlevel.log.NewLineBorderFormatter
import cn.imrhj.cowlevel.ui.activity.MainActivity
import cn.imrhj.cowlevel.utils.CacheUtils
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.zxy.recovery.core.Recovery
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


/**
 * Created by rhj on 23/02/2018.
 */
class App : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        lateinit var app: App
    }

    private lateinit var mLastResumeActivity: WeakReference<Activity>

    override fun onCreate() {
        super.onCreate()
        app = this
        initOnUiThread()
    }

    private fun initOnUiThread() {
        val config = LogConfiguration.Builder()
                .logLevel(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.WARN)
                .tag("rhjlog")
                .borderFormatter(NewLineBorderFormatter())
                .build()
        val androidPrinter = AndroidPrinter()
        val filePrinter = FilePrinter.Builder(CacheUtils.logFilePath)
                .fileNameGenerator(DateFileNameGenerator())
                .backupStrategy(NeverBackupStrategy())
                .build()
        XLog.init(config, androidPrinter, filePrinter)
        registerActivityLifecycleCallbacks(this)
        Recovery.getInstance()
                .debug(BuildConfig.DEBUG)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity::class.java)
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .recoverEnabled(true)
                .callback(AppRecoveryCallback())
                .init(this)
    }

    fun getLastActivity(): Activity {
        return requireNotNull(mLastResumeActivity.get()) { "App activity shouldn't bee null" }
    }


    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
        activity?.let { mLastResumeActivity = WeakReference(it) }
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }
}