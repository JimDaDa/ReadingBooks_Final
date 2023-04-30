package com.example.readingbooks_final.database;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocal.init(getApplicationContext());

    }
}
