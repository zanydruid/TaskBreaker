package com.universe.zany.taskbreaker.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.viewmodels.MonthViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class MonthFragment extends Fragment {

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private MonthViewModel viewModel;
    private int mYear;
    private int mMonth;

    private TextView monthTextView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mYear = getArguments().getInt(YEAR);
        mMonth = getArguments().getInt(MONTH);
        //viewModel = ViewModelProviders.of(this).get(MonthViewModel.class);
        //viewModel.init(mYear, mMonth);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_month, container, false);
        monthTextView = viewGroup.findViewById(R.id.test_month_test_view);

        Locale locale = Locale.getDefault();
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, mMonth);
        monthTextView.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));

        return viewGroup;
    }
}
