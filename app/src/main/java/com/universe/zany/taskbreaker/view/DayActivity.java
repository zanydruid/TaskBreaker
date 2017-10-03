package com.universe.zany.taskbreaker.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.util.BaseActivity;

public class DayActivity extends BaseActivity {

    private static final String DAY_TAG = "day_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        FragmentManager manager = getSupportFragmentManager();
        DayFragment fragment = (DayFragment) manager.findFragmentByTag(DAY_TAG);
        Bundle bundle = getIntent().getExtras();
        int year = bundle.getInt("today_year");
        int month = bundle.getInt("today_month");
        int day = bundle.getInt("today_day");

        if (fragment == null) {
            fragment = DayFragment.newInstance(year, month, day);
        }

        addFragmentToActivity(
                manager,
                fragment,
                R.id.activity_day_root,
                DAY_TAG
        );
    }
}
