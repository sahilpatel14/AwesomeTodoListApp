package com.example.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.data.common.DataConstants;
import com.example.data.common.DataUtils;
import com.example.db.dao.TaskDao;
import com.example.db.entities.TaskEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;



import static org.hamcrest.Matchers.*;


import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

    public static final String TASK_TITLE = "Test Title";
    public static final String TASK_ID = DataUtils.generateRandomId();

    private static TaskEntity taskEntity;

    private TaskDao mTaskDao;
    private RoomDb mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, RoomDb.class).build();
        mTaskDao = mDb.taskDao();

        taskEntity = getTaskEntity();
    }

    @After
    public void closeDb() throws Exception {
        mDb.close();
    }


    @Test
    public void insert_writeTaskEntity_returnLong() {
        Long response = mTaskDao.insertTask(taskEntity);
        System.out.print(response);
        assertThat(response, is(1L));
    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void insert_passNull_throwsException() {
//        mTaskDao.insertTask(null);
//    }
//
//    @Test
//    public void getTasks_passNullStatusType_returnsEmptyList() {
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(null);
//        response.subscribe(
//                taskEntities -> assertEquals(taskEntities.size(), 0)
//        );
//    }
//
//    @Test
//    public void getTasks_passInvalidStatusType_returnsEmptyList() {
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks("Invalid status");
//        response.subscribe(
//                taskEntities -> assertEquals(taskEntities.size(), 0),
//                throwable -> {}
//        );
//    }
//
//
//    @Test
//    public void getTasks_passActiveStatusType_returnsOneItem() {
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(DataConstants.STATE_ACTIVE);
//        response.subscribe(
//                taskEntities -> assertEquals(taskEntities.size(), 1),
//                throwable -> {}
//        );
//    }
//
//    @Test
//    public void getTasks_passCompletedStatusType_returnsEmptyList() {
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(DataConstants.STATE_COMPLETED);
//        response.subscribe(
//                taskEntities -> assertEquals(taskEntities.size(), 1),
//                throwable -> {}
//        );
//    }
//
//    @Test
//    public void updateTask_passNull_throwsException() {
//        mTaskDao.updateTask(null);
//    }
//
//    @Test
//    public void updateTask_passSameEntityObject_returnsOne() {
//        int response = mTaskDao.updateTask(taskEntity);
//        assertThat(response, is(1));
//    }
//
//    @Test
//    public void updateTask_passUpdatedEntityObject_returnsZero() {
//        TaskEntity updatedTaskEntity = taskEntity;
//        updatedTaskEntity.setTaskTitle("Updated task title");
//
//        int response = mTaskDao.updateTask(taskEntity);
//        assertThat(response, is(0));
//    }
//
//    @Test
//    public void updateTask_passNonExistentEntityObject_returnsZero() {
//        TaskEntity notTaskEntity = taskEntity;
//        notTaskEntity.setTaskId(DataUtils.generateRandomId());
//        notTaskEntity.setTaskTitle("Updated task title");
//
//        int response = mTaskDao.updateTask(notTaskEntity);
//        assertThat(response, is(0));
//    }


    @Test
    public void deleteTask_passExistingEntityObject_returnsOne() {
        int response = mTaskDao.deleteTask(taskEntity);
        assertThat(response, is(1));
    }

//    @Test
//    public void deleteTask_passingNonExistingEntityObject_returnsZero() {
//        taskEntity.setTaskId("1224");
//        int response = mTaskDao.deleteTask(taskEntity);
//        assertThat(response, is(0));
//    }

    private TaskEntity getTaskEntity() {
        if (taskEntity == null) {
            taskEntity = new TaskEntity(
                    TASK_ID,
                    "Some Test Title",
                    DataConstants.STATE_ACTIVE,
                    System.currentTimeMillis());
        }
        return taskEntity;
    }
}
