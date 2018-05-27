package com.example.data.sources;



import com.example.data.models.Task;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by sahil-mac on 12/05/18.
 */

public interface TaskDataSource {

    /**
     * This is our Contract between Presenters/ Repositories and
     * Data sources. Presenters expect data sources to provide data
     * in the decided format whenever these methods are called.
     *
     * For any new Data Source, i.e. a remote API, we will require
     * the new Data Source to implement this interface and provide
     * data for same calls.
     *
     * This way we can guarantee de coupling between data layer and
     * business logic.
     */

    Flowable<List<Task>> getAllActiveTasks();
    Flowable<List<Task>> getAllCompletedTasks();

    Observable<Boolean> saveTask(Task task);
    Observable<Boolean> updateTaskAsCompleted(Task task);
    Observable<Boolean> deleteTask(Task task);
}
