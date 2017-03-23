package com.droidrui.updatedemo;

import android.app.Application;

/**
 * Created by lingrui on 2017/3/19.
 */

public class App extends Application {

    private static App sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = (App) getApplicationContext();
    }

    public static App getContext() {
        return sContext;
    }
}
