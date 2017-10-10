package com.universe.zany.taskbreaker.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.injection.MainApplication;
import com.universe.zany.taskbreaker.viewmodels.HomeViewModel;

import java.util.Calendar;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

    @Inject
    ViewModelProvider.Factory factory;
    // views
    private Button taskInMonthButton;
    private Button taskInDayButton;
    private Button newTaskButton;
    private Button settingButton;
    private TextView createdTextView;
    private TextView failedTextView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        taskInMonthButton = view.findViewById(R.id.frg_home_month_button);
        taskInMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MonthPageViewActivity.class);
                startActivity(intent);
            }
        });

        taskInDayButton = view.findViewById(R.id.frg_home_today_button);
        taskInDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DayActivity.class);
                Bundle bundle = new Bundle();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                bundle.putInt("today_year", year);
                bundle.putInt("today_month", month);
                bundle.putInt("today_day", day);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        newTaskButton = view.findViewById(R.id.frg_home_newtask_button);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateActivity.class);
                Bundle bundle = new Bundle();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                bundle.putInt("today_year", year);
                bundle.putInt("today_month", month);
                bundle.putInt("today_day", day);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        settingButton = view.findViewById(R.id.frg_home_setting_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        createdTextView = view.findViewById(R.id.frg_home_created_textview);
        failedTextView = view.findViewById(R.id.frg_home_failed_textview);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainApplication)getActivity().getApplication()).getTaskComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);

        viewModel.getNumOfCreatedTask().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                String created = "Created: " + String.valueOf(integer);
                createdTextView.setText(created);
            }
        });

        viewModel.getNumOfFailedTask().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                String failed = "Failed: " + String.valueOf(integer);
                failedTextView.setText(failed);
            }
        });
    }
}
