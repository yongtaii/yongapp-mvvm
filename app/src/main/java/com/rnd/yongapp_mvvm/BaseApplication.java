package com.rnd.yongapp_mvvm;

import android.app.Application;

public class BaseApplication extends Application {
    private static BaseApplication app;

    public BaseApplication() {
        super();
        app = this;
    }

    public static BaseApplication getInstance() {
        return app;
    }

    public static String getResourceString(int resId) {
        return getInstance().getString(resId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}

