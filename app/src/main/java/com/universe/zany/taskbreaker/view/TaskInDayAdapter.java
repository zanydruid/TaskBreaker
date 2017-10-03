package com.universe.zany.taskbreaker.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.core.Task;

import java.util.List;


public class TaskInDayAdapter extends RecyclerView.Adapter<TaskInDayAdapter.TaskHolder> {

    private final List<Task> tasks;


    public TaskInDayAdapter(List<Task> tasks) {
        this.tasks = tasks;

    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_task, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskHolder holder, int position) {
        Task currentTask = tasks.get(position);
        String content;
        if (currentTask.getContent().length() > 50) {
            content = currentTask.getContent().substring(0, 50) + "...";
        } else {
            content = currentTask.getContent();
        }
        holder.contentTextView.setText(content);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        protected final TextView contentTextView;

        public TaskHolder(View view) {
            super(view);
            contentTextView = view.findViewById(R.id.card_task_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentTextView.getText() + "'";
        }
    }
}
