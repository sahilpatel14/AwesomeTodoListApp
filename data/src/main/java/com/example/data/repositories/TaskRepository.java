package com.example.data.repositories;




import com.example.data.models.Task;
import com.example.data.sources.TaskDataSource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * Created by sahil-mac on 13/05/18.
 */

/**
 * Repository might look useless since we only have one kind of Data Source.
 * However when data sources are more than one, Repo channels data requests to
 * different type sources based on the request.
 *
 * A request for tasks will go to roomDs, a request for some preference will go
 * to shared preference, all this happens in a Repo.
 */
public class TaskRepository implements TaskDataSource {

    private static TaskRepository INSTANCE;

    @NonNull
    private TaskDataSource mLocalDataSource;

    /**
     * Repo requires all the data sources which have entity(Task) data. Therefore
     * data sources must be sent to Repo during initialisation.
     */
    public static TaskRepository getInstance(@NonNull TaskDataSource taskDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TaskRepository(taskDataSource);
        }
        return INSTANCE;
    }

    private TaskRepository(@NonNull TaskDataSource localDataSource) {
        this.mLocalDataSource = localDataSource;
    }

    @Override
    public Flowable<List<Task>> getAllActiveTasks() {
        return mLocalDataSource.getAllActiveTasks();
    }

    @Override
    public Flowable<List<Task>> getAllCompletedTasks() {
        return mLocalDataSource.getAllCompletedTasks();
    }

    @Override
    public Observable<Boolean> saveTask(Task task) {
        return mLocalDataSource.saveTask(task);
    }

    @Override
    public Observable<Boolean> updateTaskAsCompleted(Task task) {
        return mLocalDataSource.updateTaskAsCompleted(task);
    }

    @Override
    public Observable<Boolean> deleteTask(Task task) {
        return mLocalDataSource.deleteTask(task);
    }
}
