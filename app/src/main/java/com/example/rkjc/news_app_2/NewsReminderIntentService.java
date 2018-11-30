package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class NewsReminderIntentService extends IntentService {


    public NewsReminderIntentService() {
        super("NewsReminderIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String intentAction = intent.getAction();
        ReminderTasks.executeTask(this, intentAction);
    }
}
