package com.coepzest.zest23;

import com.google.firebase.analytics.FirebaseAnalytics;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        getFireBaseMsg(message.getNotification().getTitle() , message.getNotification().getBody());
    }


    public void getFireBaseMsg(String title , String msg){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"myFirebaseChannel")
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(101, builder.build());
    }
}
