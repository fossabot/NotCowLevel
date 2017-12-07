package cn.imrhj.cowlevel;

import android.app.Application;
import android.graphics.Bitmap;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.squareup.picasso.Picasso;

import cn.imrhj.cowlevel.network.downloader.OkHttp3Downloader;


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
        Picasso picasso = new Picasso.Builder(getApplicationContext())
                .indicatorsEnabled(BuildConfig.DEBUG)
                .loggingEnabled(BuildConfig.DEBUG)
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .downloader(new OkHttp3Downloader(getApplicationContext()))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    public static App getApplication() {
        return mInstance;
    }
}
