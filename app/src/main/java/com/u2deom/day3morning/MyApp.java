package com.u2deom.day3morning;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.utils.CommonApplication;
import com.example.pages.BuildConfig;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        SignCheck(this).check();
        CommonApplication.commonApp = this;
        ARouter.openLog();     // Print log
        ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        ARouter.init(this);


    }
}
