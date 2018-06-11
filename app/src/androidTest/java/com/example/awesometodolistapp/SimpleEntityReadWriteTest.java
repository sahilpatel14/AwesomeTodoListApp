package com.example.awesometodolistapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.awesometodolistapp.data.common.DataConstants;
import com.example.awesometodolistapp.data.common.DataUtils;
import com.example.awesometodolistapp.data.sources.local.database.RoomDb;
import com.example.awesometodolistapp.data.sources.local.database.dao.TaskDao;
import com.example.awesometodolistapp.data.sources.local.database.entities.TaskEntity;

import static org.hamcrest.Matchers.*;

import org.junit.After;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

/**
 * Created by sahil-mac on 17/05/18.
 */

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

    public static final String TASK_TITLE = "Test Title";
    public static final String TASK_ID = "bla bla bla";

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
    public void rowCount_returnsNoOfRows_returnZero() {
        assertThat(mTaskDao.rowCount(), is(0));
    }

    @Test
    public void insert_writeTaskEntity_returnLong() {
        Long response = mTaskDao.insertTask(taskEntity);
        assertThat(response, is(1L));
    }

    @Test
    public void getTask_getSavedTask_returnsTask() {

        Long resp = mTaskDao.insertTask(taskEntity);
        assertThat(resp, is(1L));

        assertThat(mTaskDao.rowCount(), is(1));

        Log.d("MyTest :", taskEntity.getTaskId());

        Flowable<TaskEntity> response = mTaskDao.getTask(taskEntity.getTaskId());
        TestSubscriber<TaskEntity> testSubscriber = new TestSubscriber<>();

        response.subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void rowCount_returnsNoOfRows_returnOne() {

        mTaskDao.insertTask(taskEntity);

        assertThat(mTaskDao.rowCount(), is(1));
    }



//    @Test(expected = IllegalArgumentException.class)
//    public void insert_passNull_throwsException() {
//        mTaskDao.insertTask(null);
//    }

//    @Test
//    public void getTasks_passNullStatusType_returnsEmptyList() {
//
//        mTaskDao.insertTask(taskEntity);
//        TestSubscriber<List<TaskEntity>> testSubscriber = new TestSubscriber<>();
//
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(null);
//        response.subscribe(testSubscriber);
//
//        testSubscriber.assertNoErrors();
//        testSubscriber.assertEmpty();
//    }

//    @Test
//    public void getTasks_passInvalidStatusType_returnsEmptyList() {
//
//
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks("Invalid status");
//        TestSubscriber<List<TaskEntity>> testTaskEntity = new TestSubscriber<>();
//        response.subscribe(testTaskEntity);
//
//        testTaskEntity.assertNoErrors();
//        testTaskEntity.assertEmpty();
//    }
//
//
//    @Test
//    public void getTasks_passActiveStatusType_returnsOneItem() {
//
//        mTaskDao.insertTask(taskEntity);
//        taskEntity.setTaskId("fdsfsd");
////        mTaskDao.insertTask(taskEntity);
//
//        TestSubscriber<List<TaskEntity>> testTaskEntity = new TestSubscriber<>();
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(DataConstants.STATE_ACTIVE);
//        response.subscribe(testTaskEntity);
//        testTaskEntity.assertNoErrors();
//        testTaskEntity.assertValues(Collections.singletonList(taskEntity));
//    }
//
//    @Test
//    public void getTasks_passCompletedStatusType_returnsEmptyList() {
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(DataConstants.STATE_COMPLETED);
//        response.subscribe(
//                taskEntities -> {
//                    System.out.print(taskEntities.get(0));
//                    Log.d("Test My code : ", taskEntities.get(0).toString());
//                    assertEquals(taskEntities.size(), 1);
//                },
//                throwable -> {}
//        );
//    }

//    @Test(expected = IllegalArgumentException.class)
//    public void updateTask_passNull_throwsException() {
//        mTaskDao.updateTask(null);
//    }

//    @Test
//    public void updateTask_passSameEntityObject_returnsZero() {
//        int response = mTaskDao.updateTask(taskEntity);
//        //  No rows updated
//        assertThat(response, is(0));
//    }

//    @Test
//    public void updateTask_passUpdatedEntityObject_returnsOne() {
//        TaskEntity updatedTaskEntity = taskEntity;
//        updatedTaskEntity.setTaskTitle("Updated task title");
//
//        int response = mTaskDao.updateTask(taskEntity);
//        assertThat(response, is(1));
//    }

//    @Test
//    public void updateTask_passNonExistentEntityObject_returnsZero() {
//        TaskEntity notTaskEntity = taskEntity;
//        notTaskEntity.setTaskId(DataUtils.generateRandomId());
//        notTaskEntity.setTaskTitle("Updated task title");
//
//        int response = mTaskDao.updateTask(notTaskEntity);
//        assertThat(response, is(0));
//    }


//    @Test
//    public void deleteTask_passExistingEntityObject_returnsOne() {
//        int response = mTaskDao.deleteTask(taskEntity);
//        assertThat(response, is(1));
//    }

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
