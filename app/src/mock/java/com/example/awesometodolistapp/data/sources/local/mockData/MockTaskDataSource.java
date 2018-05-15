package com.example.awesometodolistapp.data.sources.local.mockData;

import android.util.Log;

import com.example.awesometodolistapp.data.models.Task;
import com.example.awesometodolistapp.data.sources.TaskDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sahil-mac on 13/05/18.
 */

public class MockTaskDataSource implements TaskDataSource {

    private static List<Task> mAllTasks = new ArrayList<>(10);
    public static int id = 1;

    private static final String TAG = "MockTaskDataSource";

    static {

        mAllTasks.add(id - 1,new Task(
                String.valueOf(id++),
                "Buy Milk",
                "ACTIVE"
        ));

        mAllTasks.add(id - 1,new Task(
                String.valueOf(id++),
                "Buy Chocolates",
                "COMPLETED"
        ));

        mAllTasks.add(id - 1,new Task(
                String.valueOf(id++),
                "Finish Homework",
                "ACTIVE"
        ));
    }


    @Override
    public Flowable<List<Task>> getAllActiveTasks() {
        return Flowable.fromIterable(mAllTasks)
                .filter(task -> task.getTaskStatus().equals("ACTIVE"));
    }

    @Override
    public Flowable<List<Task>> getAllCompletedTasks() {
        return Flowable.fromIterable(mAllTasks)
                .filter(task -> task.getTaskStatus().equals("COMPLETED"));
    }

    @Override
    public Observable<Boolean> saveTask(Task task) {
        return Observable.fromCallable(() -> {
            mAllTasks.add(id - 2, task);
            return true;
        }).observeOn(Schedulers.computation());
    }

    @Override
    public Observable<Boolean> updateTaskAsCompleted(Task task) {
        Observable.fromIterable(mAllTasks)
                .filter(t->t.getTaskId().equals(task.getTaskId()))
                .forEach(t -> mAllTasks.remove(t));

        return saveTask(task);



    }

    @Override
    public Observable<Boolean> deleteTask(Task task) {

        return Observable.fromCallable( ()-> {
            try {
                int id = Integer.parseInt(task.getTaskId());
                mAllTasks.remove(id);
                return true;
            }
            catch (NumberFormatException e){
                Log.e(TAG, "deleteTask: ", e);
                throw e;
            }
        });
    }
}
