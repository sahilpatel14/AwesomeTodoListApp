package com.example.awesometodolistapp;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.awesometodolistapp.data.common.DataConstants;
import com.example.awesometodolistapp.data.common.DataUtils;
import com.example.awesometodolistapp.data.sources.local.database.RoomDb;
import com.example.awesometodolistapp.data.sources.local.database.dao.TaskDao;
import com.example.awesometodolistapp.data.sources.local.database.entities.TaskEntity;

import static org.hamcrest.Matchers.*;

import org.junit.After;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.observers.BaseTestConsumer;
import io.reactivex.subscribers.TestSubscriber;

/**
 * Created by sahil-mac on 17/05/18.
 */

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
        mDb = Room.inMemoryDatabaseBuilder(context, RoomDb.class)
                .allowMainThreadQueries()
                .build();
        mTaskDao = mDb.taskDao();
        taskEntity = getTaskEntity();
    }

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insert_writeTaskEntity_returnLong() {
        Long response = mTaskDao.insertTask(taskEntity);
        assertThat(response, is(1L));
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void insert_passNull_throwsException() {
//        mTaskDao.insertTask(null);
//    }

    @Test
    public void getTasks_passNullStatusType_returnsEmptyList()  {
//        mTaskDao.insertTask(taskEntity);


        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks("blob");
        response.test()
                .awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_1MS, 5000)
                .assertNoErrors()
                .assertNoTimeout()
                .assertNoValues();
    }

    @Test
    public void getTasks_passInvalidStatusType_returnsEmptyList() {
        mTaskDao.insertTask(taskEntity);

        TestSubscriber<List<TaskEntity>> taskListSubscriber = new TestSubscriber<>();
        taskListSubscriber.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_1MS, 5000);


        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(null);
        response.subscribe(taskListSubscriber);

        taskListSubscriber.assertTimeout();
        taskListSubscriber.assertValueCount(0);
        taskListSubscriber.assertNoErrors();
    }


//    @Test
//    public void getTasks_passActiveStatusType_returnsOneItem() {
//        mTaskDao.insertTask(taskEntity);
//
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(DataConstants.STATE_ACTIVE);
//        TestSubscriber<List<TaskEntity>> taskListSubscriber = new TestSubscriber<>();
//        taskListSubscriber.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_1MS, 1000);
//
//        response.subscribe(taskListSubscriber);
//
//        taskListSubscriber.assertTimeout();
//        taskListSubscriber.assertNoErrors();
//        taskListSubscriber.assertValueCount(1);
//    }

//    @Test
//    public void getTasks_passCompletedStatusType_returnsEmptyList() {
//        Flowable<List<TaskEntity>> response = mTaskDao.getAllTasks(DataConstants.STATE_COMPLETED);
//
//        TestSubscriber<List<TaskEntity>> taskListSubscriber = new TestSubscriber<>();
//        taskListSubscriber.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_1MS, 3000);
//
//        response.subscribe(taskListSubscriber);
//
//        taskListSubscriber.assertTimeout();
//        taskListSubscriber.assertNoErrors();
//        taskListSubscriber.assertValueCount(0);
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

//    @Test
//    public void updateTask_passUpdatedEntityObject_returnsZero() {
//        TaskEntity updatedTaskEntity = taskEntity;
//        updatedTaskEntity.setTaskTitle("Updated task title");
//
//        int response = mTaskDao.updateTask(taskEntity);
//        assertThat(response, is(0));
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
