package com.universe.zany.taskbreaker.view;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.service.DailyNotifyService;
import com.universe.zany.taskbreaker.util.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettingFragment extends Fragment implements TimePickerDialog.OnTimeSetListener{

    private static final int DAILY_NOTIFICATION_ALARM_ID = 901;

    private TimePickerFragment timePicker;
    private Calendar dailyNotificationCal;
    private AlarmManager mAlarmManager;
    // views
    private Switch dailyNotificationSwitch;
    private TextView dailyNotificationTimePicker;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timePicker = TimePickerFragment.newDialog(this);
        dailyNotificationCal = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        // daily notification ===== start
        dailyNotificationSwitch = view.findViewById(R.id.frg_setting_daily_notify_switch);
        dailyNotificationSwitch.setChecked(getDailyNotificationState());
        dailyNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dailyNotificationTimePicker.setVisibility(View.VISIBLE);

                } else {
                    dailyNotificationTimePicker.setVisibility(View.INVISIBLE);
                    cancelDailyNotification();
                }
            }
        });

        dailyNotificationTimePicker = view.findViewById(R.id.frg_setting_dailynotify_timepicker_textview);
        dailyNotificationTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.show(getFragmentManager(), "time_picker");
            }
        });
        dailyNotificationTimePicker.setText(getDailyNotificationTime());
        if (!getDailyNotificationState()) {
            dailyNotificationTimePicker.setVisibility(View.INVISIBLE);
        }
        // daily notification ===== end

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dailyNotificationSwitch.setChecked(getDailyNotificationState());
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        dailyNotificationCal.set(Calendar.HOUR_OF_DAY, i);
        dailyNotificationCal.set(Calendar.MINUTE, i1);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(dailyNotificationCal.getTime());
        dailyNotificationTimePicker.setText(time);
        saveDailyNotificationTime(time);
        setDailyNotification();
    }

    //==================Daily notification================== start

    public void setDailyNotification() {
        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent dailyNotifyIntent = new Intent(getContext(), DailyNotifyService.class);
        PendingIntent dailyNotifyPendingIntent = PendingIntent.getService(getContext(), DAILY_NOTIFICATION_ALARM_ID,
                dailyNotifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // set alarm
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dailyNotificationCal.getTimeInMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, dailyNotifyPendingIntent);
        saveDailyNotificationState(true);
        Toast.makeText(getContext(), "Daily notification on.", Toast.LENGTH_SHORT).show();
    }

    public void cancelDailyNotification() {
        NotificationManager notiManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notiManager.cancel(DailyNotifyService.DAILY_NOTIFICATION_ID);

        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent dailyNotifyIntent = new Intent(getContext(), DailyNotifyService.class);
        PendingIntent dailyNotifyPendingIntent = PendingIntent.getService(getContext(), DAILY_NOTIFICATION_ALARM_ID,
                dailyNotifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.cancel(dailyNotifyPendingIntent);
        dailyNotifyPendingIntent.cancel();
        saveDailyNotificationState(false);
        Toast.makeText(getContext(), "Daily notification off.", Toast.LENGTH_SHORT).show();
    }

    public void saveDailyNotificationState(boolean state) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.daily_notification_state), state);
        editor.commit();
    }

    public boolean getDailyNotificationState() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(getString(R.string.daily_notification_state), false);
    }

    public void saveDailyNotificationTime(String time) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.daily_notification_time), time);
        editor.commit();
    }

    public String getDailyNotificationTime() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.daily_notification_time), "Pick a time");
    }

    //==================Daily notification================== end
}
