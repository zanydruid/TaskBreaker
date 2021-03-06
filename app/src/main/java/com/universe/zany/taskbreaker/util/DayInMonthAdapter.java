package com.universe.zany.taskbreaker.util;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.core.Day;
import com.universe.zany.taskbreaker.core.Month;

import java.util.Calendar;


public class DayInMonthAdapter extends RecyclerView.Adapter<DayInMonthAdapter.TaskDayViewHolder> {

    private int mYear;
    private Month  mMonth;
    private View.OnClickListener mListener;

    public DayInMonthAdapter(int year, Month month) {
        mYear = year;
        mMonth = month;
    }

    @Override
    public TaskDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_day, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });
        return new TaskDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskDayViewHolder holder, int position) {
        Day currentDay = mMonth.getALlDays().get(position);


        // set background color
        boolean passed = false;
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.YEAR) > mYear
                && cal.get(Calendar.MONTH) > mMonth.getMonth()) {
            passed = true;
        } else if (cal.get(Calendar.MONTH) == mMonth.getMonth()
                && cal.get(Calendar.DAY_OF_MONTH) > currentDay.getDay()) {
            passed = true;
        }

        // day belongs to previous month
        if (currentDay.getDay() < 0) {
            holder.background.setBackgroundColor(Color.BLACK);
        } else {
            holder.textView.setText(String.valueOf(currentDay.getDay()));
            if (passed) {
                holder.background.setBackgroundColor(Color.GRAY);
            } else {
                switch (currentDay.getStatus()) {
                    case Day.EMPTY:
                        holder.background.setBackgroundColor(Color.WHITE);
                        break;
                    case Day.CASUAL:
                        holder.background.setBackgroundColor(Color.YELLOW);
                        break;
                    case Day.BUSY:
                        holder.background.setBackgroundColor(Color.GREEN);
                        break;
                    case Day.SUPER_BUSY:
                        holder.background.setBackgroundColor(Color.RED);
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMonth.getSize();
    }

    public void setItemClickListener(View.OnClickListener callback) {
        mListener = callback;
    }


    public static class TaskDayViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView;
        protected ImageView background;

        public TaskDayViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.card_view_text_view);
            background = itemView.findViewById(R.id.card_view_image_background);
        }
    }
}
