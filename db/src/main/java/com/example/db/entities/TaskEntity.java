package com.example.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.data.models.Task;

import java.util.Date;

/**
 * Created by sahil-mac on 12/05/18.
 */

/**
 * This entity represents Task table, a row in Task
 * to be precise. ColumnInfo annotation represents their actual
 * name in db.
 */
@Entity(tableName = "task_table")
public class TaskEntity {

    /*
     *   task_table
     *   id -> String [PrimaryKey]
     *   status -> String
     *   title -> String
     *   description -> String
     *   deadline -> Long [epoch time in ms]
     *   created_at -> Long [epoch time in ms]
     */

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String mTaskId;

    @NonNull
    @ColumnInfo(name = "status")
    private String mTaskStatus;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTaskTitle;

    @ColumnInfo(name = "description")
    private String mTaskDescription;

    @ColumnInfo(name = "deadline")
    private Long mTaskDeadline;

    @NonNull
    @ColumnInfo(name = "created_at")
    private Long mCreatedAt;

    public TaskEntity(@NonNull String taskId, @NonNull String taskTitle,
                      @NonNull String taskStatus, @NonNull Long createdAt) {
        this.mTaskId = taskId;
        this.mTaskTitle = taskTitle;
        this.mTaskStatus = taskStatus;
        this.mCreatedAt = createdAt;
    }


    /**
     * this method converts Task object to TaskEntity object.
     * We will receive Task from business layer and we adapt it
     * to TaskEntity before saving it to db.
     */
    public static TaskEntity create(@NonNull Task task) {
        TaskEntity taskEntity = new TaskEntity(
                task.getTaskId(), task.getTaskTitle(),
                task.getTaskStatus(), task.getCreatedAt().getTime());

        taskEntity.mTaskDescription = task.getTaskDescription();
        taskEntity.mTaskDeadline = task.getTaskDeadline() != null ?
                task.getTaskDeadline().getTime() :
                null;
        return taskEntity;
    }

    /**
     * While fetching data from roomDS, we need to convert room specific
     * task object (TaskEntity) into Task object. This happens in the
     * create static method.
     */
    public static Task create(@NonNull TaskEntity eTask) {
        Task task = new Task(eTask.getTaskId(), eTask.getTaskTitle(), eTask.getTaskStatus());
        task.setTaskDescription(eTask.getTaskDescription());
        task.setCreatedAt(new Date(eTask.getCreatedAt()));
        task.setTaskDeadline(eTask.getTaskDeadline() != null ?
                new Date(eTask.getTaskDeadline()) :
                null);
        return task;
    }

    public String getTaskId() {
        return mTaskId;
    }

    public void setTaskId(String taskId) {
        this.mTaskId = taskId;
    }

    public String getTaskStatus() {
        return mTaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.mTaskStatus = taskStatus;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.mTaskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String taskDescription) {this.mTaskDescription = taskDescription;}

    public Long getTaskDeadline() {
        return mTaskDeadline;
    }

    public void setTaskDeadline(Long taskDeadline) {
        this.mTaskDeadline = taskDeadline;
    }

    public Long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.mCreatedAt = createdAt;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj instanceof TaskEntity) {
            TaskEntity task = (TaskEntity) obj;
            return this == task || mTaskId.equals(task.getTaskId());
        }
        return false;

    }
}
