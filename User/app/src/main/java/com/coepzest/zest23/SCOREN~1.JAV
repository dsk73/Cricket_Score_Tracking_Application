package com.coepzest.zest23;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.coepzest.zest23.R;

public class NotificationScore {

    private static final String CHANNEL_ID = "LiveScoreChannel";
    private static final String CHANNEL_NAME = "Live Score Channel";

    private Context context;
    private NotificationManager notificationManager;

    public NotificationScore(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create the notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(String title, String message) {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_icon);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setLargeIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        Notification notification = builder.build();
        notificationManager.notify(0, notification);
    }
}
