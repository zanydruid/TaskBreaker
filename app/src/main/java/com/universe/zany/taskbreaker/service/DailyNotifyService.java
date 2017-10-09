package com.universe.zany.taskbreaker.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.universe.zany.taskbreaker.view.DayActivity;

public class DailyNotifyService extends IntentService{

    private static final String TAG = "DailyNotifyService";

    public DailyNotifyService() {
        super("Daily notification service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Setting notification...");
        Intent notifyIntent = new Intent(getApplicationContext(), DayActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notifyIntent, 0);
        int numOfTasks = intent.getExtras().getInt("daily_num_tasks");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Task Breaker")
                .setContentText("You have " + numOfTasks + " tasks today.")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        synchronized (notificationManager) {
            notificationManager.notify();
            notificationManager.notify(1, builder.build());
        }
    }
}
