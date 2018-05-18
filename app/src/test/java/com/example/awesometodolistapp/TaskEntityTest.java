package com.example.awesometodolistapp;

import com.example.awesometodolistapp.data.common.DataConstants;
import com.example.awesometodolistapp.data.common.DataUtils;
import com.example.awesometodolistapp.data.models.Task;
import com.example.awesometodolistapp.data.sources.local.database.entities.TaskEntity;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by sahil-mac on 17/05/18.
 */

public class TaskEntityTest {

    public static final String TASK_TITLE = "Test Title";
    public static final String TASK_ID = DataUtils.generateRandomId();

    @Test(expected = IllegalArgumentException.class)
    public void taskEntityConstructor1_passingNull_throwsException() {
        TaskEntity taskEntity = new TaskEntity(null, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskEntityConstructor1_passingEmptyString_throwsException() {
        TaskEntity taskEntity = new TaskEntity("", "", "", 0L);
    }

    @Test
    public void taskEntityConstructor1_passingString_returnsTaskEntity() {
        Long createdAt = System.currentTimeMillis();
        String status = DataConstants.STATE_ACTIVE;
        TaskEntity taskEntity = new TaskEntity(TASK_ID, TASK_TITLE, status, createdAt);

        assertEquals(taskEntity.getCreatedAt(), createdAt);
        assertEquals(taskEntity.getTaskId(), TASK_ID);
        assertEquals(taskEntity.getTaskTitle(), TASK_TITLE);
        assertEquals(taskEntity.getTaskStatus(), status);
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskCreate_passingNull_throwsException() {
        TaskEntity task = TaskEntity.create(null);
    }

    @Test
    public void taskCreate_passingTask_createsTaskEntityObject() {

        String status = DataConstants.STATE_ACTIVE;
        Task task = new Task(TASK_ID, TASK_TITLE, status);

        Long createdAt = task.getCreatedAt().getTime();
        TaskEntity entity = TaskEntity.create(task);

        assertEquals(entity.getCreatedAt(), createdAt);
        assertEquals(entity.getTaskTitle(), TASK_TITLE);
        assertEquals(entity.getTaskStatus(), status);
        assertEquals(entity.getTaskId(), TASK_ID);
    }
}
