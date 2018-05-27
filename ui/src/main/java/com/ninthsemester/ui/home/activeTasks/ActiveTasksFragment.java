package com.ninthsemester.ui.home.activeTasks;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.data.models.Task;
import com.example.ui.R;
import com.ninthsemester.ui.addTask.AddTaskDialog;
import com.ninthsemester.ui.common.Constants;
import com.ninthsemester.ui.home.tasks.ScreenType;
import com.ninthsemester.ui.home.tasks.TasksFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveTasksFragment extends TasksFragment
        implements ActiveTasksContract.View {

    private ActiveTasksPresenter mPresenter;

    public ActiveTasksFragment() {
        super.screenType = ScreenType.ACTIVE;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ActiveTasksPresenter(taskRepository, this);
    }

    @Override
    public void setLoadingIndicator(boolean isActive) {
        progressBar.setVisibility(isActive? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        llListEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setTasks(tasks);
    }

    @Override
    public void openAddTaskWindow() {
        AddTaskDialog dialog = new AddTaskDialog();
        dialog.show(getChildFragmentManager(), Constants.ADD_TASK_FRAGMENT_TAG);
    }

    @Override
    public void showNoTasks() {
        recyclerView.setVisibility(View.GONE);
        llListEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(String message) {
        super.showErrorMessage(message);
    }

    @Override
    public void showInformationMessage(String message) {
        super.showInformationalText(message);
    }

    @Override
    public void showTaskCompleted(Task task) {
        super.showInformationalText(getString(R.string.message_task_completed));
    }

    @Override
    public void onTaskSelected(Task task) {
        mPresenter.setTaskAsCompleted(task);
    }

    @Override
    public void onFabClicked() {
        mPresenter.requestedAddTaskWindow();
    }

    @Override
    public void setPresenter(ActiveTasksContract.Presenter presenter) {}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
