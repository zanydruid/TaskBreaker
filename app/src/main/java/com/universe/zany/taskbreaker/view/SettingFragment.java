package com.universe.zany.taskbreaker.view;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.service.DailyNotifyService;
import com.universe.zany.taskbreaker.util.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettingFragment extends Fragment implements TimePickerDialog.OnTimeSetListener{

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

        dailyNotificationTimePicker = view.findViewById(R.id.frg_setting_dailynotify_timepicker_textview);
        dailyNotificationTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.show(getFragmentManager(), "time_picker");
            }
        });
        dailyNotificationTimePicker.setVisibility(View.INVISIBLE);

        dailyNotificationSwitch = view.findViewById(R.id.frg_setting_daily_notify_switch);
        dailyNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // TODO make time picker visible
                    dailyNotificationTimePicker.setVisibility(View.VISIBLE);

                } else {
                    // TODO cancel notification
                    // TODO make time picker invisible
                    dailyNotificationTimePicker.setVisibility(View.INVISIBLE);

                }
            }
        });

        return view;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        dailyNotificationCal.set(Calendar.HOUR_OF_DAY, i);
        dailyNotificationCal.set(Calendar.MINUTE, i1);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(dailyNotificationCal.getTime());
        dailyNotificationTimePicker.setText(time);
        //setDailyNotification();
    }

    //==================Daily notification==================

    public void setDailyNotification() {
        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent dailyNotifyIntent = new Intent(getContext(), DailyNotifyService.class);
        PendingIntent dailyNotifyPendingIntent = PendingIntent.getService(getContext(), 1,
                dailyNotifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // set alarm
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dailyNotificationCal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, dailyNotifyPendingIntent);
    }

    public void cancelDailyNotification() {

    }
}
