package com.example.awesometodolistapp.data.sources.local.database;

import android.content.Context;

import com.example.awesometodolistapp.data.common.DataConstants;
import com.example.awesometodolistapp.data.models.Task;
import com.example.awesometodolistapp.data.sources.TaskDataSource;
import com.example.awesometodolistapp.data.sources.local.database.dao.TaskDao;
import com.example.awesometodolistapp.data.sources.local.database.entities.TaskEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sahil-mac on 12/05/18.
 */

/**
 * Room implementing TaskDataSource to provide data to TaskRepository. This
 * is the central location from where Task related data that comes from db
 * will originate from.
 */
public class RoomTaskDataSource implements TaskDataSource, DataConstants {

    private static final String TAG = "RoomTaskDataSource";

    private final TaskDao mTaskDao;

    public RoomTaskDataSource(Context context) {
        RoomDb db = RoomDb.getInstance(context);
        mTaskDao = db.taskDao();
    }

    @Override
    public Flowable<List<Task>> getAllActiveTasks() {

        /*
         * Takes a list of TaskEntity objects as input and
         * returns a list of Task objects. All this along with
         * wrapping it around a Flowable.
         */
        return mTaskDao.getAllTasks(STATE_ACTIVE)
                .flatMapSingle(task->
                    Observable.fromIterable(task)
                        .map(Task::create)
                        .toList()
                );
    }

    @Override
    public Flowable<List<Task>> getAllCompletedTasks() {

        /*
         * Takes a list of TaskEntity objects as input and
         * returns a list of Task objects. All this along with
         * wrapping it around a Flowable.
         */
        return mTaskDao.getAllTasks(STATE_COMPLETED)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMapSingle(task->
                        Observable.fromIterable(task)
                                .map(Task::create)
                                .toList()
                );
    }

    @Override
    public Observable<Boolean> saveTask(Task task) {
        //  Saves Task in db after converting it into
        //  TaskEntity. If it returns a long value greater than zero,
        //  then our task was saved successfully.
        return Observable.fromCallable(() ->
                mTaskDao.insertTask(TaskEntity.create(task)) > 0)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> updateTaskAsCompleted(Task task) {

        //  If a row was affected with the query, we can be
        //  sure that row was updated
        return Observable.fromCallable(() ->
                mTaskDao.updateTask(TaskEntity.create(task)) == 1)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> deleteTask(Task task) {

        //  If a row was affected with the query, we can be
        //  sure that row was deleted
        return Observable.fromCallable(() ->
                mTaskDao.deleteTask(TaskEntity.create(task)) == 1)
                .subscribeOn(Schedulers.io());
    }
}
