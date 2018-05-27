package com.example.awesometodolistapp.common;

import android.content.Context;
import android.support.annotation.NonNull;


import com.example.awesometodolistapp.notifications.NotificationManager;
import com.example.data.repositories.TaskRepository;
import com.example.data.sources.TaskDataSource;
import com.example.db.RoomTaskDataSource;

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
