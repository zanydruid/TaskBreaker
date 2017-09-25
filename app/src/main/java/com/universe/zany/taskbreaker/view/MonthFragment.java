package com.universe.zany.taskbreaker.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.viewmodels.MonthViewModel;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import injection.MainApplication;


public class MonthFragment extends Fragment {

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private int mYear;
    private int mMonth;
    private MonthViewModel viewModel;

    @Inject ViewModelProvider.Factory factory;
    private TextView monthTextView;
    private List<Task> mTaskList;

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

        ((MainApplication) getActivity().getApplication())
                .getTaskComponent().inject(this);

        mYear = getArguments().getInt(YEAR);
        mMonth = getArguments().getInt(MONTH);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // setup viewmodel here
        viewModel = ViewModelProviders.of(this, factory).get(MonthViewModel.class);

        viewModel.getTasksByMonth(mYear, mMonth).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                mTaskList = tasks;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_month, container, false);
        monthTextView = viewGroup.findViewById(R.id.test_month_test_view);

        Locale locale = Locale.getDefault();
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, mMonth);
        monthTextView.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + " " + mYear);

        return viewGroup;
    }
}
