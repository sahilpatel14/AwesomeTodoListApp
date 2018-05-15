package com.example.awesometodolistapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.awesometodolistapp.data.repositories.TaskRepository;
import com.example.awesometodolistapp.data.sources.TaskDataSource;
import com.example.awesometodolistapp.data.sources.local.mockData.MockTaskDataSource;

/**
 * Created by sahil-mac on 13/05/18.
 */

public final class Injection {


    public static TaskRepository getTaskRepository(@NonNull Application application) {
        TaskDataSource taskDataSource = new MockTaskDataSource();
        return TaskRepository.getInstance(taskDataSource);
    }

    public static NotificationManager getNotificationManager(@NonNull Context context){
        return new NotificationManager(context.getApplicationContext());
    }
}
