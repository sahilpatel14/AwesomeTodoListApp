package com.example.data;

import com.example.data.common.DataConstants;
import com.example.data.common.DataUtils;
import com.example.data.models.Task;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskTest {

    public static final String TASK_TITLE = "Test Title";
    public static final String TASK_ID = DataUtils.generateRandomId();

    @Test(expected = IllegalArgumentException.class)
    public void taskConstructor1_passingNull_CompileTimeError() {
        Task task = new Task(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskConstructor1_passingEmptyString_throwsIllegalArgumentException() {
        Task task = new Task("");
    }

    @Test
    public void taskConstructor1_passingString_returnsTaskObject() {
        Task task = new Task(TASK_TITLE);
        assertNotNull(task.getTaskId());
        assertEquals(task.getCreatedAt(), DataUtils.getCurrentDate());
        assertEquals(task.getTaskStatus(), DataConstants.STATE_ACTIVE);
        assertEquals(task.getTaskTitle(), TASK_TITLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskConstructor2_passingNull_CompileTimeError() {
        Task task = new Task(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskConstructor2_passingEmptyString_throwsIllegalArgumentException() {
        Task task = new Task("", "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskConstructor2_passingInvalidState_throwsIllegalArgumentException() {
        Task task = new Task(TASK_ID, TASK_TITLE, "Some state");
    }

    @Test
    public void taskConstructor2_passingString_returnsTaskObject() {
        Task task = new Task(TASK_ID, TASK_TITLE, DataConstants.STATE_ACTIVE);
        assertEquals(task.getCreatedAt(), DataUtils.getCurrentDate());
        assertEquals(task.getTaskStatus(), DataConstants.STATE_ACTIVE);
        assertEquals(task.getTaskTitle(), TASK_TITLE);
        assertEquals(task.getTaskId(), TASK_ID);
    }
}
