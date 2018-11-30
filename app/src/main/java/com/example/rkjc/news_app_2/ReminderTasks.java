package com.example.rkjc.news_app_2;

import android.content.Context;

import com.example.rkjc.news_app_2.utils.NotificationsUtils;

public class ReminderTasks {


    public static final String REFRESH_NEWS = "REFRESH_NEWS";
    public static final String CLEAR_NOTIFICATIONS = "CLEAR_NOTIFICATIONS";
    public static final String SEND_SYNC_NOTIFICATION = "SEND_SYNC_NOTIFICATION";

    public static void executeTask(Context context, String action){

        if(REFRESH_NEWS.equals(action)){
            NewsItemsRepository.syncDB();
            NotificationsUtils.clearNotifications(context);
        }else if(CLEAR_NOTIFICATIONS.equals(action)){
            NotificationsUtils.clearNotifications(context);
        }else if(SEND_SYNC_NOTIFICATION.equals(action)){
            NotificationsUtils.remindUserToSync(context);
        }

    }
}
