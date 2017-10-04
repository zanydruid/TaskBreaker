package com.universe.zany.taskbreaker.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.universe.zany.taskbreaker.core.Month;
import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.injection.MainApplication;
import com.universe.zany.taskbreaker.util.DayInMonthAdapter;
import com.universe.zany.taskbreaker.util.DayItemDecorator;
import com.universe.zany.taskbreaker.viewmodels.MonthViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


public class MonthFragment extends Fragment {

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final int GRID_COLUMN_PORTRAIT = 7;
    private static final int GRID_COLUMN_LANDSCAPE = 7;
    private int mYear;
    private int mMonth;
    private MonthViewModel viewModel;
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
        viewModel = ViewModelProviders.of(this, factory).get(MonthViewModel.class);

        // observe task list
        viewModel.getTasksByMonth(mYear, mMonth).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                month = Month.fillInTasks(tasks, mYear, mMonth);
                updateGrid(month);
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
                // start createActivity
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

        return viewGroup;
    }

    private void updateGrid(final Month month) {
        GridLayoutManager manager;
        if (getActivity().getResources()
                .getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            manager = new GridLayoutManager(getContext(), GRID_COLUMN_PORTRAIT);
        } else {
            manager = new GridLayoutManager(getContext(), GRID_COLUMN_LANDSCAPE);
        }

        recyclerView.setLayoutManager(manager);

        // add divider
        DayItemDecorator decorator = new DayItemDecorator(6);
        recyclerView.addItemDecoration(decorator);

        dayAdapter = new DayInMonthAdapter(mYear, month);
        dayAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = recyclerView.indexOfChild(view);
                if (pos >= month.getPRESET()) {
                    Intent intent = new Intent(getActivity(), DayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("today_year", mYear);
                    bundle.putInt("today_month", mMonth);
                    bundle.putInt("today_day", pos + 1 - month.getPRESET());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(dayAdapter);
    }
}
