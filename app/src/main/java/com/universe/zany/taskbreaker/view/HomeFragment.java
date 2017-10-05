package com.universe.zany.taskbreaker.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.universe.zany.taskbreaker.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private Button taskInMonthButton;
    private Button taskInDayButton;
    private Button newTaskButton;

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

        return view;
    }

}
