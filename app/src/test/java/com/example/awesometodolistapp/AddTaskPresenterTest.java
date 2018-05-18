package com.example.awesometodolistapp;

import com.example.awesometodolistapp.addTask.AddTaskContract;
import com.example.awesometodolistapp.addTask.AddTaskPresenter;
import com.example.awesometodolistapp.data.common.DataConstants;
import com.example.awesometodolistapp.data.common.DataUtils;
import com.example.awesometodolistapp.data.models.Task;
import com.example.awesometodolistapp.data.repositories.TaskRepository;
import com.example.awesometodolistapp.notifications.NotificationManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.Date;

/**
 * Created by sahil-mac on 18/05/18.
 */

public class AddTaskPresenterTest {

    public static final long oneHourInMillis = 1000 * 60 * 60;

    private static Task incorrectTask;
    private static Task correctTask;

    static {

        incorrectTask = new Task(
                DataUtils.generateRandomId(),
                "Some Test Title",
                DataConstants.STATE_ACTIVE);

        correctTask = new Task(
                DataUtils.generateRandomId(),
                "Some Test Title",
                DataConstants.STATE_ACTIVE);

        //  older date
        long deadline = System.currentTimeMillis() - oneHourInMillis;
        incorrectTask.setTaskDeadline(new Date(deadline));

        //  older date
        deadline = System.currentTimeMillis() + oneHourInMillis;
        correctTask.setTaskDeadline(new Date(deadline));
    }

    @Mock
    private TaskRepository mTaskRepository;

    @Mock
    private AddTaskContract.View mView;

    @Mock
    private NotificationManager mNotificationManager;

    private AddTaskPresenter mAddTaskPresenter;


    @Before
    public void setupNotesPresenter() {
        MockitoAnnotations.initMocks(this);

        mAddTaskPresenter = new AddTaskPresenter(mTaskRepository, mNotificationManager, mView);
    }


    @Test
    public void clickedSaveTask_ShowsTaskListScreen() {
        mAddTaskPresenter.requestedTaskListWindow();
        verify(mView).showTaskListWindow();
    }

    @Test
    public void submitTask_submitInvalidDate_ShowsErrorMessage() {
        mAddTaskPresenter.saveTask(incorrectTask);
        verify(mView).showValidationErrorMessage(AddTaskContract.View.ValidationFields.DEADLINE,"invalid deadline selected");
    }
}
