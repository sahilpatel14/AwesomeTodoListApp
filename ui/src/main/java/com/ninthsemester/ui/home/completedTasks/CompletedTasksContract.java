package com.ninthsemester.ui.home.completedTasks;


import com.example.data.models.Task;
import com.ninthsemester.ui.common.BasePresenter;
import com.ninthsemester.ui.common.BaseView;

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
