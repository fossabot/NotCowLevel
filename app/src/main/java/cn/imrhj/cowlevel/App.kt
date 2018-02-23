package cn.imrhj.cowlevel

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import java.lang.ref.WeakReference

/**
 * Created by rhj on 23/02/2018.
 */
class App : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        lateinit var app: App
    }

    lateinit var mLastResumeActivity: WeakReference<Activity>

    override fun onCreate() {
        super.onCreate()
        app = this
        initOnUiThread()
    }

    private fun initOnUiThread() {
        XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
    }

    fun getLastActivity(): Activity {
        return mLastResumeActivity.get()!!
    }


    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
        if (activity != null) {
            mLastResumeActivity = WeakReference(activity)
        }
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