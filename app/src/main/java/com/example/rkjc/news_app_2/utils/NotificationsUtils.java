package com.example.rkjc.news_app_2.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.rkjc.news_app_2.MainActivity;
import com.example.rkjc.news_app_2.NewsReminderIntentService;
import com.example.rkjc.news_app_2.R;
import com.example.rkjc.news_app_2.ReminderTasks;

public class NotificationsUtils {

    private static final String NEWS_REMINDER_NOTIFICATION_CHANNE_ID = "NEWS_REMINDER_NOTIFICATION_ID";
    private static final int REMINDER_NOTIFICATION_ID = 9140;
    private static final int PENDING_INTENT_ID = 1234;
    private static final int IGNORE_PENDING_INTENT_ID = 5432;
    private  static final int SYNC_PENDING_INTENT_ID = 6577;

    public static void clearNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserToSync(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NEWS_REMINDER_NOTIFICATION_CHANNE_ID, context.getString(R.string.main_notification_channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NEWS_REMINDER_NOTIFICATION_CHANNE_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setSmallIcon(R.drawable.ic_arrow_downward_black_24dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle("SyncTime")
                .setContentText("It's about time you synced me. . .")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("It's about time you synced me. . ."))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(ignoreNotification(context))
                .addAction(syncNotification(context))
                .setAutoCancel(true);

        notificationManager.notify(REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    // purpose of this method is so that when the user clicks on the notification
    // the application is relaunched
    private static PendingIntent contentIntent(Context context){
        Intent startMainActivity = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, PENDING_INTENT_ID, startMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_android_black_24dp);
        return largeIcon;
    }

    private static NotificationCompat.Action ignoreNotification(Context context){
        Intent ignoreIntent = new Intent(context, NewsReminderIntentService.class);
        ignoreIntent.setAction(ReminderTasks.CLEAR_NOTIFICATIONS);
        PendingIntent ignorePendingIntent = PendingIntent.getService(context, IGNORE_PENDING_INTENT_ID, ignoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreAction = new NotificationCompat.Action(R.drawable.ic_clear_black_24dp, "Maybe Later", ignorePendingIntent);
        return ignoreAction;
    }

    private static NotificationCompat.Action syncNotification(Context context){
        Intent syncIntent = new Intent(context, NewsReminderIntentService.class);
        syncIntent.setAction(ReminderTasks.REFRESH_NEWS);
        PendingIntent syncPendingIntent = PendingIntent.getService(context, SYNC_PENDING_INTENT_ID, syncIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreAction = new NotificationCompat.Action(R.drawable.ic_clear_black_24dp, "Sync Now", syncPendingIntent);
        return ignoreAction;
    }



}
