package com.universe.zany.taskbreaker.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.util.BaseActivity;

public class HomeActivity extends BaseActivity {

    private static final String HOME_TAG = "home_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager manager = getSupportFragmentManager();

        HomeFragment fragment = (HomeFragment) manager.findFragmentByTag(HOME_TAG);

        if (fragment == null) {
            fragment = HomeFragment.newInstance();
        }

        addFragmentToActivity(
                manager,
                fragment,
                R.id.activity_home_root,
                HOME_TAG
        );
    }
}
