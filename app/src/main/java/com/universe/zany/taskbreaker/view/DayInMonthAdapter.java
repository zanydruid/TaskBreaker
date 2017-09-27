package com.universe.zany.taskbreaker.view;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.core.Day;
import com.universe.zany.taskbreaker.core.Month;

import java.util.Calendar;
import java.util.List;


public class DayInMonthAdapter extends RecyclerView.Adapter<DayInMonthAdapter.TaskDayViewHolder> {

    private Month  mMonth;

    public DayInMonthAdapter(Month month) {
        mMonth = month;
    }

    @Override
    public TaskDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_day, parent, false);
        return new TaskDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskDayViewHolder holder, int position) {
        Day currentDay = mMonth.getDays().get(position);
        holder.textView.setText(String.valueOf(currentDay.getDay()));

        // set background color
        boolean passed = false;
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MONTH) > mMonth.getMonth()) {
            passed = true;
        } else if (cal.get(Calendar.MONTH) == mMonth.getMonth()
                && cal.get(Calendar.DAY_OF_MONTH) > currentDay.getDay()) {
            passed = true;
        }

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

    @Override
    public int getItemCount() {
        return mMonth.getDays().size();
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
