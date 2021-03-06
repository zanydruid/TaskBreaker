package com.universe.zany.taskbreaker.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.util.BaseActivity;

public class CreateActivity extends BaseActivity {

    private static final String CREATE_TAG = "create_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        FragmentManager manager = getSupportFragmentManager();
        CreateFragment fragment = (CreateFragment) manager.findFragmentByTag(CREATE_TAG);
        Bundle bundle = getIntent().getExtras();
        int year = bundle.getInt("today_year");
        int month = bundle.getInt("today_month");
        int day = bundle.getInt("today_day");

        if (fragment == null) {
            fragment = CreateFragment.newInstance(year, month, day);
        }

        addFragmentToActivity(
                manager,
                fragment,
                R.id.activity_create_root,
                CREATE_TAG
        );
    }
}
