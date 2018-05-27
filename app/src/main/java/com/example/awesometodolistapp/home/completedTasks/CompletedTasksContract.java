package com.example.awesometodolistapp.home.completedTasks;

import com.example.awesometodolistapp.common.BasePresenter;
import com.example.awesometodolistapp.common.BaseView;
import com.example.data.models.Task;

import java.util.List;

/**
 * Created by sahil-mac on 14/05/18.
 */

interface CompletedTasksContract {


    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean isActive);
        void showTasks(List<Task> tasks);
        void openAddTaskWindow();
        void showNoTasks();
        void showInformationMessage(String message);
        void showErrorMessage(String message);
        void showTaskAsDeleted(Task task);
    }

    interface Presenter extends BasePresenter {

        void loadTasks();
        void requestedAddTaskWindow();
        void deleteTask(Task task);
    }
}
