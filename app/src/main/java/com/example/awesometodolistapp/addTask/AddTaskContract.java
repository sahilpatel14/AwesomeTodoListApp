package com.example.awesometodolistapp.addTask;

import com.example.awesometodolistapp.common.BasePresenter;
import com.example.awesometodolistapp.common.BaseView;
import com.example.awesometodolistapp.data.models.Task;

/**
 * Created by sahil-mac on 14/05/18.
 */

interface AddTaskContract {

    interface View extends BaseView<Presenter> {

        //  We have three fields in our presentation layer. We
        //  use this enum to pin point which field has error
        //  during the validation phase.
        enum ValidationFields { TITLE, DEADLINE, DESCRIPTION }

        void setLoadingIndicator(boolean isActive);
        void showTaskListWindow();
        void showErrorMessage(String errorMessage);
        void showValidationErrorMessage(ValidationFields field, String message);
        void showInformationalMessage(String message);
        void showTaskCreatedMessage();
    }

    interface Presenter extends BasePresenter {

        void saveTask(Task task);
        void requestedTaskListWindow();
        boolean validateTask(Task task);
    }
}
