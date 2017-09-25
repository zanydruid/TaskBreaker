package com.universe.zany.taskbreaker.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.universe.zany.taskbreaker.core.Day;

import java.util.List;


public class DayInMonthAdapter extends BaseAdapter {

    private Context mContext;
    private List<Day> days;

    public DayInMonthAdapter(Context context, List<Day> days) {
        this.mContext = context;
        this.days = days;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int i) {
        return days.get(i);
    }

    @Override
    public long getItemId(int i) {
        return days.get(i).getDay();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView dummyView = new TextView(mContext);
        dummyView.setText(String.valueOf(days.get(i).getDay()));
        return dummyView;
    }
}
