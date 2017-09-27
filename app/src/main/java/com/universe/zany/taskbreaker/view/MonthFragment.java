package com.universe.zany.taskbreaker.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.core.Day;
import com.universe.zany.taskbreaker.core.Month;
import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.viewmodels.TaskViewModel;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import com.universe.zany.taskbreaker.injection.MainApplication;


public class MonthFragment extends Fragment {

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private int mYear;
    private int mMonth;
    private TaskViewModel viewModel;
    private Month month;
    @Inject ViewModelProvider.Factory factory;
    // views
    private TextView monthTextView;
    private RecyclerView recyclerView;
    private DayInMonthAdapter dayAdapter;
    private Button createButton;


    public static MonthFragment newInstance(int year, int month) {
        MonthFragment fragment = new MonthFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(YEAR, year);
        bundle.putInt(MONTH, month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // dependencies injection
        ((MainApplication) getActivity().getApplication())
                .getTaskComponent().inject(this);

        // get arguments
        mYear = getArguments().getInt(YEAR);
        mMonth = getArguments().getInt(MONTH);

        // set up month
        month = new Month(mYear, mMonth);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // setup viewmodel here
        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel.class);

        // observe task list
        viewModel.getTasksByMonth(mYear, mMonth).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                month.fillInTasks(tasks);
                updateList(month.getDays());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_month, container, false);
        // header textview
        monthTextView = viewGroup.findViewById(R.id.frg_month_text_view);
        Locale locale = Locale.getDefault();
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, mMonth);
        monthTextView.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + " " + mYear);

        // recycler view
        recyclerView = viewGroup.findViewById(R.id.frg_month_recycler_view);

        // create button
        createButton = viewGroup.findViewById(R.id.frg_month_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                // start createActivity
            }
        });

        return viewGroup;
    }

    private void updateList(List<Day> days) {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 5);
        recyclerView.setLayoutManager(manager);
        dayAdapter = new DayInMonthAdapter(days);
        recyclerView.setAdapter(dayAdapter);
    }
}
