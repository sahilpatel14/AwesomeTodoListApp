package com.example.awesometodolistapp.addTask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awesometodolistapp.Injection;
import com.example.awesometodolistapp.R;
import com.example.awesometodolistapp.common.Constants;
import com.example.awesometodolistapp.common.Utils;
import com.example.awesometodolistapp.data.models.Task;

import java.util.Calendar;
import java.util.Date;

import static com.example.awesometodolistapp.common.Constants.DATE_TIME_DISPLAY_FORMAT;
import static com.example.awesometodolistapp.common.Utils.getInputFromField;

/**
 * Created by sahil-mac on 14/05/18.
 */

public class AddTaskDialog extends DialogFragment
        implements View.OnClickListener, AddTaskContract.View {

    private static final String TAG = "AddTaskDialog";

    private EditText etTitle;
    private EditText etDeadline;
    private EditText etDescription;
    private ProgressBar progressBar;

    private Calendar selectedDate;
    private AddTaskContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add_task, container, false);
        initViews(rootView);

        if (getContext() == null) {
            throw new IllegalStateException("getContext() is null. It shouldn't be null.");
        }

        mPresenter = new AddTaskPresenter(
                Injection.getTaskRepository(getContext()),
                Injection.getNotificationManager(getContext()),
                this);

        return rootView;
    }

    private void initViews(View rootView) {

        etTitle = rootView.findViewById(R.id.et_add_task_title);
        etDeadline = rootView.findViewById(R.id.et_add_task_deadline);
        etDescription = rootView.findViewById(R.id.et_add_task_description);

        progressBar = rootView.findViewById(R.id.add_task_progress_bar);
        Button btnSave = rootView.findViewById(R.id.btn_add_task_save);
        TextView btnCancel = rootView.findViewById(R.id.btn_add_task_cancel);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        etDeadline.setOnClickListener(this);
    }


    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean isActive) {
        progressBar.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTaskListWindow() {
        getDialog().dismiss();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        AlertDialog alertDialog = Utils.simpleAlertDialog(getContext(), errorMessage);
        alertDialog.show();
    }

    @Override
    public void showValidationErrorMessage(ValidationFields field, String message) {
        switch (field) {
            case TITLE:
                etTitle.setError(message);
                break;
            case DEADLINE:
                etDeadline.setError(message);
                break;
            case DESCRIPTION:
                etDescription.setError(message);
        }
    }

    @Override
    public void showInformationalMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showTaskCreatedMessage() {
        showInformationalMessage(getString(R.string.message_new_task_created));
        mPresenter.requestedTaskListWindow();
    }

    private void saveTask() {

        //  getting all input fields entered by the user.

        String title = getInputFromField(etTitle);
        Task task = new Task(title);

        if (selectedDate != null)
            task.setTaskDeadline(selectedDate.getTime());

        String description = getInputFromField(etDescription);
        task.setTaskDescription(description);

        //  If validation succeeds, we call save task
        if (mPresenter.validateTask(task))
            mPresenter.saveTask(task);

    }

    private void openTimePicker() {

        //  if this is the first time that we are selecting a date,
        //  we initialise it with current date. This will be used
        //  to open date and time instance at a particular instance.
        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
        }

        //  Setting current date of calendar to last selected user's date
        final Calendar currentDate = selectedDate;

        //  A listener to listen to time selected by user.
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {

            //  Once the user has selected a time, we update it to
            //  selectedDate.
            selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedDate.set(Calendar.MINUTE, minute);

            Log.d(TAG, "Final selected date : "+selectedDate.getTime());

            //  Finally updating this selected date in deadline edit text.
            String displayDate = Utils.getDateInFormat(selectedDate.getTime(), DATE_TIME_DISPLAY_FORMAT);
            etDeadline.setText(displayDate);
        };

        //  A listener to listen to date selected by user.
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {

                    //  updating selected date to our global selected date variable.
                    selectedDate.set(year, month, dayOfMonth);

                    //  User has already selected a Time. Next we use Time picker to pick a time.
                    new TimePickerDialog(
                            getContext(), timeSetListener, currentDate.get(Calendar.HOUR_OF_DAY),
                            currentDate.get(Calendar.MINUTE), false)
                            .show();
                };


        if (getContext() == null) {
            throw new IllegalStateException("getContext() is null. It shouldn't be null.");
        }

        //  Starting date picker
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener,
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-10000);
        datePickerDialog.show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_add_task_deadline:
                openTimePicker();
                break;
            case R.id.btn_add_task_save:
                saveTask();
                break;
            case R.id.btn_add_task_cancel:
                mPresenter.requestedTaskListWindow();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        //  Customising dialog to give varying dimensions. Match Parent
        //  and Wrap Content
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null){
                dialog.getWindow().setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //  Customising Dialog to not show the Title and disable
        //  dismissal when clicking outside the dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }
}
