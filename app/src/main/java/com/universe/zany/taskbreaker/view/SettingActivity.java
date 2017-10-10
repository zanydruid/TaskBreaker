package com.universe.zany.taskbreaker.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.util.BaseActivity;

public class SettingActivity extends BaseActivity {

    private static final String SETTING_TAG = "setting_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        FragmentManager manager = getSupportFragmentManager();
        SettingFragment fragment = (SettingFragment) manager.findFragmentByTag(SETTING_TAG);
        if (fragment == null) {
            fragment = SettingFragment.newInstance();
        }
        addFragmentToActivity(
                manager,
                fragment,
                R.id.activity_setting_root,
                SETTING_TAG
        );
    }
}
