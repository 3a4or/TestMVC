package com.ashour.test;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static synchronized Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

}
