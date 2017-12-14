package cn.imrhj.cowlevel;

import android.app.Application;
import android.content.Context;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;


/**
 * Created by rhj on 2017/12/4.
 */

public class App extends Application {
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initInUIThread();
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

    public static App getApplication() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
