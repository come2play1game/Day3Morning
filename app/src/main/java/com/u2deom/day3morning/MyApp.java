package com.u2deom.day3morning;

import android.app.Application;

import com.u2deom.day3morning.util.SignCheck;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        new SignCheck(this).check();
    }
}
