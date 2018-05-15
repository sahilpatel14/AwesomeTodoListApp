package com.example.awesometodolistapp.data.sources.local.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.awesometodolistapp.data.sources.local.database.entities.TaskEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by sahil-mac on 12/05/18.
 */


/**
 * Contains all our queries. We are running two separate
 * queries for getting ACTIVE Tasks and COMPLETED tasks.
 * That is because, filtering in sql is generally faster than
 * fetching data and then filtering it.
 */
@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTask(TaskEntity task);

    @Delete
    int deleteTask(TaskEntity task);

    @Query("SELECT * FROM task_table WHERE status = :taskStatus ORDER BY created_at DESC")
    Flowable<List<TaskEntity>> getAllTasks(String taskStatus);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateTask(TaskEntity updatedTask);
}
