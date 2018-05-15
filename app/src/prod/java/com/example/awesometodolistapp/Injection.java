package com.example.awesometodolistapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.awesometodolistapp.data.repositories.TaskRepository;
import com.example.awesometodolistapp.data.sources.TaskDataSource;
import com.example.awesometodolistapp.data.sources.local.database.RoomTaskDataSource;
import com.example.awesometodolistapp.notifications.NotificationManager;

/**
 * Created by sahil-mac on 13/05/18.
 */

public final class Injection {


    public static TaskRepository getTaskRepository(@NonNull Context context) {
        TaskDataSource taskDataSource = new RoomTaskDataSource(context);
        return TaskRepository.getInstance(taskDataSource);
    }

    public static NotificationManager getNotificationManager(@NonNull Context context){
        return new NotificationManager(context.getApplicationContext());
    }
}
