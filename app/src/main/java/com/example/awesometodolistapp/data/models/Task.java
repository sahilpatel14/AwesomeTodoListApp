package com.example.awesometodolistapp.data.models;
import com.example.awesometodolistapp.data.sources.local.database.entities.TaskEntity;
import java.util.Date;

import static com.example.awesometodolistapp.data.common.DataConstants.STATE_ACTIVE;
import static com.example.awesometodolistapp.data.common.DataUtils.generateRandomId;
import static com.example.awesometodolistapp.data.common.DataUtils.getCurrentDate;

/**
 * Created by sahil-mac on 12/05/18.
 */


/**
 * For our whole application, Task is what contains the entity data.
 * Before saving we convert task in to source specific entity.
 */
public final class Task {

    private final String mTaskId;
    private final String mTaskTitle;
    private String mTaskStatus;
    private String mTaskDescription;
    private Date mCreatedAt;
    private Date mTaskDeadline;

    /**
     * While fetching data from roomDS, we need to convert room specific
     * task object (TaskEntity) into Task object. This happens in the
     * create static method.
     */
    public static Task create(TaskEntity eTask) {
        Task task = new Task(eTask.getTaskId(), eTask.getTaskTitle(), eTask.getTaskStatus());
        task.mTaskDescription = eTask.getTaskDescription();
        task.mCreatedAt = new Date(eTask.getCreatedAt());
        task.mTaskDeadline = eTask.getTaskDeadline() != null ?
                new Date(eTask.getTaskDeadline()) :
                null;
        return task;
    }

    /**
     * This constructor is used to create a new task. When user
     * starts filling the form to create a new task, this constructor
     * creates the new task.
     */
    public Task(String taskTitle) {
        this.mTaskId = generateRandomId();
        this.mCreatedAt = getCurrentDate();
        this.mTaskStatus = STATE_ACTIVE;
        this.mTaskTitle = taskTitle;
    }

    /**
     * This constructor is used internally by create method to convert TaskEntity
     * into Task object.
     */
    public Task(String taskId, String taskTitle, String taskStatus) {
        this.mTaskId = taskId;
        this.mTaskTitle = taskTitle;
        this.mTaskStatus = taskStatus;
        this.mCreatedAt = new Date();
    }


    public String getTaskId() {
        return mTaskId;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public String getTaskStatus() {
        return mTaskStatus;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.mTaskDescription = taskDescription;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.mCreatedAt = createdAt;
    }

    public Date getTaskDeadline() {
        return mTaskDeadline;
    }

    public void setTaskDeadline(Date taskDeadline) {
        this.mTaskDeadline = taskDeadline;
    }

    public void setTaskStatus(String taskStatus) {
        this.mTaskStatus = taskStatus;
    }
}
