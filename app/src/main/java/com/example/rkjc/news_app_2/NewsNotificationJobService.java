package com.example.rkjc.news_app_2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NewsNotificationJobService extends JobService {
    private AsyncTask mbackgroundTask;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters params) {
        mbackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = NewsNotificationJobService.this;
                NewsItemsRepository repo = new NewsItemsRepository(context);
//                NewsItemsRepository.syncDB();
                ReminderTasks.executeTask(context, ReminderTasks.SEND_SYNC_NOTIFICATION);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
               jobFinished(params, false);
            }
        };
        mbackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (mbackgroundTask != null) mbackgroundTask.cancel(false);
        return true;
    }
}
