package com.universe.zany.taskbreaker.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.util.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthPageViewActivity extends BaseActivity {


    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private int mYear;
    private List<Integer> mMonths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonths = new ArrayList<>();
        int month = cal.get(Calendar.MONTH);
        for(int i = 0; i < 6; i++) {
            mMonths.add(month + i);
        }

        viewPager = findViewById(R.id.month_pager);
        pagerAdapter = new MonthPagerAdapter(getSupportFragmentManager(), mYear, mMonths);
        viewPager.setAdapter(pagerAdapter);
    }

    private class MonthPagerAdapter extends FragmentStatePagerAdapter {

        private List<Integer> months;
        private int year;

        public MonthPagerAdapter(FragmentManager fm, int year, List<Integer> months) {
            super(fm);
            this.year = year;
            this.months = months;
        }

        @Override
        public Fragment getItem(int position) {

            return MonthFragment.newInstance(this.year, this.months.get(position));
        }

        @Override
        public int getCount() {
            return months.size();
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}
