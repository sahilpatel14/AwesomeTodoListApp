package com.example.db;

import com.example.data.common.DataConstants;
import com.example.data.common.DataUtils;
import com.example.data.models.Task;
import com.example.db.entities.TaskEntity;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Date;

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


    @Test
    public void taskEntityCreate_passingTaskEntity_ReturnTaskObject() {
        long createdAt = System.currentTimeMillis();
        TaskEntity taskEntity = new TaskEntity(
                TASK_ID, TASK_TITLE,
                DataConstants.STATE_ACTIVE,createdAt);

        Task task = TaskEntity.create(taskEntity);
        assertEquals(task.getTaskId(), TASK_ID);
        assertEquals(task.getTaskStatus(), DataConstants.STATE_ACTIVE);
        assertEquals(task.getTaskTitle(), TASK_TITLE);
        assertEquals(task.getCreatedAt(), new Date(createdAt));
    }
}
