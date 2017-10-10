package com.universe.zany.taskbreaker.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.universe.zany.taskbreaker.data.repository.TaskRepository;
import com.universe.zany.taskbreaker.injection.MainApplication;
import com.universe.zany.taskbreaker.view.CreateActivity;
import com.universe.zany.taskbreaker.view.DayActivity;

import java.util.Calendar;

import javax.inject.Inject;

import static com.universe.zany.taskbreaker.util.SyncLiveData.getValue;

public class DailyNotifyService extends IntentService{

    private static final String TAG = "DailyNotifyService";
    public static final int DAILY_NOTIFICATION_ID = 10000;

    @Inject
    TaskRepository repo;

    public DailyNotifyService() {
        super("Daily notification service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((MainApplication)getApplication())
                .getTaskComponent()
                .inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Setting notification...");
        Calendar cal = Calendar.getInstance();
        Intent notifyIntent;
        Bundle bundle = new Bundle();
        bundle.putInt("today_year", cal.get(Calendar.YEAR));
        bundle.putInt("today_month", cal.get(Calendar.MONTH));
        bundle.putInt("today_day", cal.get(Calendar.DAY_OF_MONTH));

        int numOfTasks = 0;
        try {
            numOfTasks = getValue(this.repo.getTasksInDay(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH))).size();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String content;
        if (numOfTasks == 0) {
            content = "You don't have task today, let's create one.";
            notifyIntent = new Intent(getApplicationContext(), CreateActivity.class);
        } else {
            content = "You have " + numOfTasks + " task(s) today.";
            notifyIntent = new Intent(getApplicationContext(), DayActivity.class);
        }

        notifyIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notifyIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Task Breaker")
                .setContentText(content)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        synchronized (notificationManager) {
            notificationManager.notify();
            notificationManager.notify(DAILY_NOTIFICATION_ID, builder.build());
        }
    }
}
