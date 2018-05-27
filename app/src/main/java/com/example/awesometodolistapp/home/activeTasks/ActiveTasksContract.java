package com.example.awesometodolistapp.home.activeTasks;

import android.support.annotation.NonNull;

import com.example.awesometodolistapp.common.BasePresenter;
import com.example.awesometodolistapp.common.BaseView;
import com.example.data.models.Task;

import java.util.List;

/**
 * Created by sahil-mac on 13/05/18.
 */

interface ActiveTasksContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isActive);
        void showTasks(List<Task> tasks);
        void openAddTaskWindow();
        void showNoTasks();
        void showInformationMessage(String message);
        void showErrorMessage(String message);
        void showTaskCompleted(Task task);
    }

    interface Presenter extends BasePresenter {

        void loadTasks();
        void requestedAddTaskWindow();
        void setTaskAsCompleted(@NonNull Task task);
    }
}
