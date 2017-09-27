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

import java.util.List;


public class DayInMonthAdapter extends RecyclerView.Adapter<DayInMonthAdapter.TaskDayViewHolder> {

    private List<Day> days;

    public DayInMonthAdapter(List<Day> days) {
        this.days = days;
    }

    @Override
    public TaskDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_day, parent, false);
        return new TaskDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskDayViewHolder holder, int position) {
        Day currentDay = days.get(position);
        holder.textView.setText(String.valueOf(currentDay.getDay()));
        if (currentDay.getTasks().size() == 0) {
            holder.background.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
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
