package com.universe.zany.taskbreaker.view;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.injection.MainApplication;
import com.universe.zany.taskbreaker.viewmodels.TaskViewModel;

import javax.inject.Inject;

public class CreateFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    @Inject
    ViewModelProvider.Factory factory;
    private TaskViewModel viewModel;
    private boolean useStartDatePicker;

    Task mTask;

    // views
    TextView startDateTextView;
    TextView deadlineTextView;
    ImageButton startDatePicker;
    ImageButton deadlinePicker;
    Spinner typeSpinner;
    TextView durationTextView;
    EditText durationInputEdit;
    Spinner durationUnitSpinner;
    EditText contentEdit;
    Button cancelButton;
    Button createButton;


    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance() {
        CreateFragment fragment = new CreateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication())
                .getTaskComponent()
                .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel.class);
        mTask = new Task();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_create, container, false);
        startDateTextView = view.findViewById(R.id.frg_create_startdatepick_textview);
        deadlineTextView = view.findViewById(R.id.frg_create_deadlinepick_textview);
        startDatePicker = view.findViewById(R.id.frg_create_startdate_pick_button);
        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO show date picker dialog
                // set useStartDatePicker to true
            }
        });
        deadlinePicker = view.findViewById(R.id.frg_create_deadline_pick_button);
        deadlinePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO show date picker dialog
                // set useStartDatePicker to false
            }
        });

        // Task type spinner
        typeSpinner = view.findViewById(R.id.frg_create_type_spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.task_type,
                android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        // Duration invisible at first
        durationTextView = view.findViewById(R.id.frg_create_duration_hint_textview);
        durationTextView.setVisibility(View.INVISIBLE);
        durationInputEdit = view.findViewById(R.id.frg_create_duration_input_edit);
        durationInputEdit.setVisibility(View.INVISIBLE);
        durationUnitSpinner = view.findViewById(R.id.frg_create_duration_spinner);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.duration_array_unit,
                android.R.layout.simple_spinner_item
        );
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationUnitSpinner.setAdapter(unitAdapter);
        durationUnitSpinner.setVisibility(View.INVISIBLE);

        contentEdit = view.findViewById(R.id.frg_create_content_edit);
        cancelButton = view.findViewById(R.id.frg_create_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO cancel creation of task, return to previous fragment
            }
        });

        createButton = view.findViewById(R.id.frg_create_ok_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO show confirmation dialog
            }
        });

        return view;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        // TODO
        // if(useStartDatePicker) set start date
        // else set deadline date
    }
}
