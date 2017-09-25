package com.universe.zany.taskbreaker.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

    public static void addFragmentToActivity(FragmentManager fm,
                                             Fragment fragment,
                                             int frameId,
                                             String tag) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commit();
    }
}
