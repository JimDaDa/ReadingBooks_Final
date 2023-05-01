package com.example.readingbooks_final.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.readingbooks_final.MainActivity;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;
import com.example.readingbooks_final.database.MyApplication;
import com.google.firebase.messaging.CommonNotificationBuilder;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification = message.getNotification();
        if (notification == null){
            return;
        }
        //Xử lý thông báo
        String stringTitle = notification.getTitle();
        String strMess = notification.getBody();
        //sendNotice(stringTitle,strMess);
      //  sendNotification(Context ,new Books_data());
    }

    //Hiển thị thông báo
    public void sendNotice(String stringTitle, String strMess) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, MyApplication.CHANEL_ID)
                .setContentTitle(stringTitle)
                .setContentText(strMess)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);
        Notification notification = notiBuilder.build();
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager!= null){
            notificationManager.notify(getNoticeID(),notification);

        }
    }
//    public static void sendNotification(Context context,Books_data books_data) {
//        Notification notification = new NotificationCompat.Builder(context)
//                .setContentTitle(books_data.getTitle())
//                .setContentText(books_data.getAuthors())
//                .setSmallIcon(R.mipmap.ic_launcher)
//              //  .setLargeIcon(Icon.createWithContentUri(books_data.getImgUrl()))
//                .build();
//        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(MyFirebaseMessagingService.getNoticeID(),notification);
//    }

    public static int getNoticeID(){
        return (int) new Date().getTime();
    }
}
