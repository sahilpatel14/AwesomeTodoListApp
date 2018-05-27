package com.example.awesometodolistapp.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


import com.example.data.models.Task;

import java.util.Date;

/**
 * Created by sahil-mac on 14/05/18.
 */

public class NotificationManager implements NotificationInfo{

    /**
     * MVP requires us to wrap context whenever we require any
     * service that is Android specific. NotificationManager is one such manager.
     * It abstracts setting up of alarm by Alarm manager based on the deadline
     * property defined in Task.
     *
     * We use Injection to create object of NotificationManager.
     */

    private static final String TAG = "NotificationManager";
    private Context mContext;


    public NotificationManager(Context context) {
        this.mContext = context;
    }


    /**
     * Schedules a notification to be triggered when its deadline.
     * If there is no deadline, then the call is ignored.
     */
    public void scheduleTask(Task task) {

        //  If user did not set up a deadline, we ignore it.
        if (task.getTaskDeadline() == null) {
            return;
        }

        //  Deadline in milliseconds
        long deadlineInMs = task.getTaskDeadline().getTime();

        //  Creating a notification based on the task
        Notification notification = NotificationUtils.createNotification(mContext, task);

        //  Creating an intent to open NotificationPublisher receiver class.
        Intent notificationIntent = new Intent(mContext, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.KEY_NOTIFICATION, notification);

        //  As notificationIntent will be triggered at a later stage, we need to wrap it around
        //  pending intent.
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                mContext, 0, notificationIntent, 0);


        Log.d(TAG, "scheduleTask: Alarm set for "+new Date(deadlineInMs));
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null){
            Log.e(TAG, "scheduleTask: AlarmManager is null.");
        }
        else {

            //  Calling setExact() for newer devices and set() for
            //  pre Kitkat devices.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, deadlineInMs, alarmPendingIntent);
            else
                alarmManager.set(AlarmManager.RTC_WAKEUP, deadlineInMs, alarmPendingIntent);

        }

    }
}
