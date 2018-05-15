package com.example.awesometodolistapp.home.activeTasks;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.awesometodolistapp.data.models.Task;
import com.example.awesometodolistapp.data.repositories.TaskRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.awesometodolistapp.data.common.DataConstants.STATE_COMPLETED;

/**
 * Created by sahil-mac on 13/05/18.
 */

class ActiveTasksPresenter implements ActiveTasksContract.Presenter {

    private static final String TAG = "ActiveTasksPresenter";

    @NonNull
    private final TaskRepository mTaskRepository;

    @NonNull
    private final ActiveTasksContract.View mView;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    ActiveTasksPresenter(@NonNull TaskRepository taskRepository,
                                @NonNull ActiveTasksContract.View view) {
        this.mTaskRepository = taskRepository;
        this.mView = view;

        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadTasks();
    }

    @Override
    public void unsubscribe() {
        //  Clearing all added disposables before closing the presenter.
        mCompositeDisposable.clear();
    }

    @Override
    public void loadTasks() {

        //  Show loading indicator before doing anything else
        mView.setLoadingIndicator(true);
        mCompositeDisposable.clear();

        //  Fetching new data from repo.
        Disposable disposable =
                mTaskRepository.getAllActiveTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tasks -> {
                            processTasks(tasks);
                            mView.setLoadingIndicator(false);
                        },
                        throwable -> {
                            handleErrorResponse(throwable);
                            mView.setLoadingIndicator(false);
                        }
                );
        mCompositeDisposable.add(disposable);
    }

    private void processTasks(List<Task> tasks) {

        Log.d(TAG, "processTask: "+tasks.size());

        if (tasks.isEmpty()) {
            mView.showNoTasks();
        } else {
            mView.showTasks(tasks);
        }
    }

    @Override
    public void requestedAddTaskWindow() {
        mView.openAddTaskWindow();
    }

    @Override
    public void setTaskAsCompleted(@NonNull Task task) {
        task.setTaskStatus(STATE_COMPLETED);
        Disposable disposable =
                mTaskRepository.updateTaskAsCompleted(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::handleTaskUpdateResponse,
                        this::handleErrorResponse
                );
        mCompositeDisposable.add(disposable);
    }

    private void handleErrorResponse(Throwable throwable) {
        Log.e(TAG, "handleErrorResponse: ",throwable);
        mView.showErrorMessage("We faced something unexpected from our end. Try again later.");
    }

    private void handleTaskUpdateResponse(boolean response) {
        if (response)
            mView.showInformationMessage("Task completed successfully.");
        else
            mView.showInformationMessage("Something went wrong. Task not updated.");
    }
}
