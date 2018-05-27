package com.example.awesometodolistapp.home.completedTasks;

import android.support.annotation.NonNull;
import android.util.Log;


import com.example.data.models.Task;
import com.example.data.repositories.TaskRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sahil-mac on 14/05/18.
 */

class CompletedTasksPresenter implements CompletedTasksContract.Presenter {

    private static final String TAG = "CompletedTasksPresenter";

    @NonNull
    private final TaskRepository mTaskRepository;

    @NonNull
    private final CompletedTasksContract.View mView;

    @NonNull
    private CompositeDisposable mCompositeDisposable;


    CompletedTasksPresenter(@NonNull TaskRepository taskRepository, @NonNull CompletedTasksContract.View view) {
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
                mTaskRepository.getAllCompletedTasks()
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

        Log.d(TAG, "processTasks: "+tasks.size());
        if (tasks.isEmpty()){
            mView.showNoTasks();
        }
        else {
            mView.showTasks(tasks);
        }
    }


    @Override
    public void deleteTask(Task task) {
        Disposable disposable =
                mTaskRepository.deleteTask(task)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::handleTaskDeletionResponse,
                                this::handleErrorResponse
                        );
        mCompositeDisposable.add(disposable);
    }

    private void handleErrorResponse(Throwable throwable) {
        Log.e(TAG, "handleErrorResponse: ",throwable);
        mView.showErrorMessage("We faced something unexpected from our end. Try again later.");
    }

    @Override
    public void requestedAddTaskWindow() {
        mView.openAddTaskWindow();
    }

    private void handleTaskDeletionResponse(boolean response) {
        if (response)
            mView.showInformationMessage("Task deleted successfully.");
        else
            mView.showInformationMessage("Something went wrong. Task not deleted.");
    }
}
