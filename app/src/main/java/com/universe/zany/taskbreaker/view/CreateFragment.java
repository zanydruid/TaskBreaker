package com.universe.zany.taskbreaker.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.universe.zany.taskbreaker.R;
import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.injection.MainApplication;
import com.universe.zany.taskbreaker.util.DatePickerFragment;
import com.universe.zany.taskbreaker.viewmodels.CreateViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

public class CreateFragment extends Fragment implements DatePickerDialog.OnDateSetListener, DialogInterface.OnClickListener{

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";

    @Inject
    ViewModelProvider.Factory factory;
    private CreateViewModel viewModel;

    //private boolean useStartDatePicker;
    private Task mTask;
    private int mYear;
    private int mMonth;
    private int mDay;
    private List<String> emptySlots;

    // views
    TextView deadlineTextView;
    ImageButton deadlinePicker;
    Spinner typeSpinner;
    TextView durationTextView;
    EditText durationInputEdit;
    Spinner durationUnitSpinner;
    EditText contentEdit;
    Button createButton;
    // dialog fragment
    DatePickerFragment datePickerFragment;


    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(int year, int month, int day) {
        CreateFragment fragment = new CreateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(YEAR, year);
        bundle.putInt(MONTH, month);
        bundle.putInt(DAY, day);
        fragment.setArguments(bundle);
        return fragment;
    }

    //=====================life cycle =====================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication())
                .getTaskComponent()
                .inject(this);

        Bundle bundle = getArguments();
        mYear = bundle.getInt(YEAR, 2017);
        mMonth = bundle.getInt(MONTH, 11);
        mDay = bundle.getInt(DAY, 31);

        // init task object
        mTask = new Task();
        mTask.setCreated(new Date());
        Calendar cal = createCal(mYear, mMonth, mDay);
        mTask.setDeadline(cal.getTime());

        datePickerFragment = DatePickerFragment.newDialog(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(CreateViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_create, container, false);

        // set up views
        // deadline text view
        Calendar cal = createCal(mYear, mMonth, mDay);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String date = format.format(cal.getTime());
        deadlineTextView = view.findViewById(R.id.frg_create_deadlinepick_textview);
        deadlineTextView.setText(date);
        deadlinePicker = view.findViewById(R.id.frg_create_deadline_pick_button);
        deadlinePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set useStartDatePicker to false
                datePickerFragment.show(getFragmentManager(), "date_picker");
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
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTask.setType(i);
                // other than single
                if (i > 0) {
                    durationTextView.setVisibility(View.VISIBLE);
                    durationInputEdit.setVisibility(View.VISIBLE);
                    durationUnitSpinner.setVisibility(View.VISIBLE);
                } else {
                    durationTextView.setVisibility(View.INVISIBLE);
                    durationInputEdit.setVisibility(View.INVISIBLE);
                    durationUnitSpinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mTask.setType(0);
            }
        });

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

        createButton = view.findViewById(R.id.frg_create_ok_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. check valid task
                if (!passTaskCheck()) {
                    StringBuilder sb = new StringBuilder();
                    for (String slot : emptySlots) {
                        sb.append(slot + "\n");
                    }
                    Toast.makeText(getContext(), "Error(s) :\n" + sb.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                // 2. build dialog title & message and show dialog
                String message = createConfirmMessage();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm_create_title)
                        .setMessage(message);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        conductCreate();
                        goBack();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setNeutralButton(R.string.another_new_task, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        conductCreate();
                        contentEdit.setText("");
                        //dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return view;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        // if(useStartDatePicker) set start date
        // else set deadline date

        Calendar cal = createCal(i, i1, i2);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String date = format.format(cal.getTime());

        deadlineTextView.setText(date);
        mTask.setDeadline(cal.getTime());

    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    private Calendar createCal(int year, int month, int day) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }

    private boolean passTaskCheck() {

        boolean passCheck = true;
        emptySlots = new ArrayList<>();

        if (mTask.getDeadline() == null) {
            emptySlots.add("Please fill in Deadline");
            passCheck = false;
        }

        if (mTask.getType() == -1) {
            emptySlots.add("Please choose a Task Type");
            passCheck = false;
        }

        if (mTask.getType() != 0 && durationInputEdit.getText().toString().length() == 0) {
            emptySlots.add("Please fill in Task Duration");
            passCheck = false;
        }

        if (contentEdit.getText().toString().length() == 0) {
            emptySlots.add("Please fill in mTask Content");
            passCheck = false;
        }

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_YEAR, -1);
        if (mTask.getDeadline().getTime() < today.getTimeInMillis()) {
            emptySlots.add("You can't create task in the past!!!");
            passCheck = false;
        }

        return passCheck;
    }

    private void conductCreate() {
        // TODO
        // debug create a task from a day.
        if (mTask.getType() == -1) {
            return;
        }

        mTask.setContent(contentEdit.getText().toString());
        if (mTask.getType() == 0) {
            viewModel.createSingleTask(new Task(mTask));
        } else {
            int base = Integer.parseInt(durationInputEdit.getText().toString());
            int multiplier = 0;
            int unit = durationUnitSpinner.getSelectedItemPosition();
            switch (unit) {
                case 0:
                    multiplier = 1;
                    break;
                case 1:
                    multiplier = 7;
                    break;
                case 2:
                    multiplier = 31;
                    break;
                case 3:
                    multiplier = 366;
                    break;
            }
            int duration = base * multiplier;

            switch (mTask.getType()) {
                case 1: // daily
                    viewModel.createDailyTask(new Task(mTask), duration);
                    break;
                case 2: // weekly
                    viewModel.createWeeklyTask(new Task(mTask), duration);
                    break;
                case 3: // monthly
                    viewModel.createMonthlyTask(new Task(mTask), duration);
                    break;
            }
        }
    }

    private String createConfirmMessage() {
        StringBuilder messageBuilder = new StringBuilder();
        // Extract task info
        String content = contentEdit.getText().toString();
        if (content.length() > 30) {
            content = content.substring(0, 30) + "...";
        }
        String deadline = deadlineTextView.getText().toString();
        String type = typeSpinner.getSelectedItem().toString();
        int typePosition = typeSpinner.getSelectedItemPosition();
        String duration = durationInputEdit.getText().toString();
        String unit = durationUnitSpinner.getSelectedItem().toString();
        // build message
        messageBuilder.append(content).append("\n");
        messageBuilder.append("Deadline: ").append(deadline).append("\n");

        if (typePosition != 0) {
            messageBuilder.append("Repeat: ").append(type).append("\n");
            messageBuilder.append("Continue for ").append(duration).append(" ").append(unit);
        }

        return messageBuilder.toString();
    }

    private void goBack() {
        getActivity().onBackPressed();
    }


}
