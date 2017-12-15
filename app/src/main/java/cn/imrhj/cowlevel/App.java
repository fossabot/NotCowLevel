package cn.imrhj.cowlevel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;


/**
 * Created by rhj on 2017/12/4.
 */

public class App extends Application implements Application.ActivityLifecycleCallbacks {
    private static App mInstance;
    private Activity mLastResumeActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initInUIThread();
        registerActivityLifecycleCallbacks(this);
    }

    private void initInUIThread() {
        XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);
//        Picasso picasso = new Picasso.Builder(getApplicationContext())
////                .indicatorsEnabled(BuildConfig.DEBUG)
//                .loggingEnabled(BuildConfig.DEBUG)
//                .defaultBitmapConfig(Bitmap.Config.RGB_565)
//                .downloader(new OkHttp3Downloader(getApplicationContext()))
//                .build();
//        Picasso.setSingletonInstance(picasso);
    }

    public Activity getLastResumeActivity() {
        return mLastResumeActivity;
    }

    public static App getApplication() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mLastResumeActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
