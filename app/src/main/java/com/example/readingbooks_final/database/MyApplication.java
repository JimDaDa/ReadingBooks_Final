package com.example.readingbooks_final.database;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {

    public static final String CHANEL_ID = "push_notification_id" ;

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocal.init(getApplicationContext());
        createChanelNotification();
    }

    private void createChanelNotification() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel(CHANEL_ID,
                    "PushNotification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            if (manager!= null){
                manager.createNotificationChannel(channel);
            }


        }
    }

}
