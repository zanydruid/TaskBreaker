package com.universe.zany.taskbreaker.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.injection.MainApplication;
import com.universe.zany.taskbreaker.view.dummy.DummyContent.DummyItem;
import com.universe.zany.taskbreaker.viewmodels.DayViewModel;

import java.util.List;

import javax.inject.Inject;


public class DayFragment extends Fragment {

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    //private OnListFragmentInteractionListener mListener;

    private int mYear;
    private int mMonth;
    private int mDay;
    private List<Task> mTasks;
    private TaskInDayAdapter adapter;

    private DayViewModel viewModel;
    @Inject
    ViewModelProvider.Factory factory;

    // views
    private RecyclerView recyclerView;
    private Button createButton;

    public DayFragment() {
    }

    public static DayFragment newInstance(int year, int month, int day) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(YEAR, year);
        args.putInt(MONTH, month);
        args.putInt(DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mYear = args.getInt(YEAR);
        mMonth = args.getInt(MONTH);
        mDay = args.getInt(DAY);
        ((MainApplication)getActivity().getApplication())
                .getTaskComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        recyclerView = view.findViewById(R.id.frg_day_recycler_view);
        createButton = view.findViewById(R.id.frg_day_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("today_year", mYear);
                bundle.putInt("today_month", mMonth);
                bundle.putInt("today_day", mDay);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(DayViewModel.class);

        viewModel.getTaskInDay(mYear, mMonth, mDay).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                mTasks = tasks;
                updateList(mTasks);
            }
        });
    }

    private void updateList(List<Task> tasks) {
        adapter = new TaskInDayAdapter(tasks);
        recyclerView.setAdapter(adapter);

    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
