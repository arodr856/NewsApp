package com.example.rkjc.news_app_2.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.rkjc.news_app_2.NewsNotificationJobService;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class ScheduleNotificationUtil {


    private static final int NOTIFICATION_INTERVAL_SECONDS = 10;
    private static final int NOTIFICATION_FLEX_SECONDS = NOTIFICATION_INTERVAL_SECONDS;
    private static final String JOB_TAG = "sync_reminder";

    private static boolean scheduleInitialized;

     synchronized public static void scheduleSyncReminder(@NonNull final Context context){
        if(scheduleInitialized) {
            return;
        }else{
            Driver driver = new GooglePlayDriver(context);
            FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);
            Job syncJob = jobDispatcher.newJobBuilder()
                    .setService(NewsNotificationJobService.class)
                    .setTag(JOB_TAG)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    .setTrigger(Trigger.executionWindow(NOTIFICATION_INTERVAL_SECONDS, NOTIFICATION_INTERVAL_SECONDS + NOTIFICATION_FLEX_SECONDS))
                    .setReplaceCurrent(true)
                    .build();
            jobDispatcher.schedule(syncJob);
            scheduleInitialized = true;
        }
    }

}
