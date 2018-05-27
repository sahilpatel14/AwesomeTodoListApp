package com.ninthsemester.ui.addTask;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.data.models.Task;
import com.example.data.repositories.TaskRepository;
import com.ninthsemester.notifications.NotificationManager;
import com.ninthsemester.ui.common.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ninthsemester.ui.addTask.AddTaskContract.View.ValidationFields.DEADLINE;
import static com.ninthsemester.ui.addTask.AddTaskContract.View.ValidationFields.DESCRIPTION;
import static com.ninthsemester.ui.addTask.AddTaskContract.View.ValidationFields.TITLE;

/**
 * Created by sahil-mac on 14/05/18.
 */

public class AddTaskPresenter implements AddTaskContract.Presenter {

    private static final String TAG = "AddTaskPresenter";

    @NonNull
    private final TaskRepository mTaskRepository;

    @NonNull
    private final AddTaskContract.View mView;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    @NonNull
    private final NotificationManager mNotificationManager;


    public AddTaskPresenter(@NonNull TaskRepository taskRepository,
                            @NonNull NotificationManager notificationManager,
                            @NonNull AddTaskContract.View view) {
        this.mTaskRepository = taskRepository;
        this.mNotificationManager = notificationManager;
        this.mView = view;

        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        //  Not required in this scenario
    }

    @Override
    public void unsubscribe() {
        //  Not required in this scenario
    }

    @Override
    public void saveTask(Task task) {

        //  cross checking if data is validated. Just to be
        //  on the safe side.
        if(validateTask(task)) {
            mView.setLoadingIndicator(true);
            mCompositeDisposable.clear();
            Disposable disposable =
                    mTaskRepository.saveTask(task)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response -> {
                                mView.setLoadingIndicator(false);
                                handleTaskCreatedResponse(response);
                            },
                            throwable -> {
                                mView.setLoadingIndicator(false);
                                handleErrorResponse(throwable);
                            }
                    );
            mNotificationManager.scheduleTask(task);
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void requestedTaskListWindow() {
        mView.showTaskListWindow();
    }

    @Override
    public boolean validateTask(Task task) {

        //  task list is a required field. It can not be empty or null
        if (task.getTaskTitle() == null || task.getTaskTitle().trim().isEmpty()) {
            mView.showValidationErrorMessage(
                    TITLE, "title can not be empty");
            return false;
        }

        //  title greater than 100 characters long should not be allowed
        if (task.getTaskTitle().length() > 100) {
            mView.showValidationErrorMessage(
                    TITLE, "title can not be greater than 100 characters long"
            );
            return false;
        }

        //  if the user has set a deadline, it should only point to a future date.
        if (task.getTaskDeadline() != null && !Utils.isDateFromFuture(task.getTaskDeadline())) {
            mView.showValidationErrorMessage(DEADLINE, "invalid deadline selected");
            return false;
        }

        //  description if set should not be greater than 200 characters.
        if (task.getTaskDescription() != null && task.getTaskDescription().length() > 200) {
            mView.showValidationErrorMessage(
                    DESCRIPTION, "description can not be greater than 200 characters long"
            );
        }
        return true;
    }

    private void handleErrorResponse(Throwable throwable) {
        Log.e(TAG, "handleErrorResponse: ",throwable);
        mView.showErrorMessage("We faced something unexpected from our end. Try again later.");
    }

    private void handleTaskCreatedResponse(boolean response) {
        if (response)
            mView.showTaskCreatedMessage();
        else
            mView.showInformationalMessage("Something went wrong. Task not created.");
    }
}
