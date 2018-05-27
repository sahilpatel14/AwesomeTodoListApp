package com.ninthsemester.ui.scheduleReminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.example.data.models.Task;
import com.example.data.repositories.TaskRepository;
import com.ninthsemester.notifications.NotificationManager;
import com.ninthsemester.ui.common.Injection;
import com.ninthsemester.ui.home.HomeActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sahil-mac on 14/05/18.
 */

public class DeviceRebootReceiver extends BroadcastReceiver {

    /**
     * Alarm Manager removes all schedules alarms when the device is rebooted.
     * This receiver will be called when device is rebooted. We are going to
     * iterate through all the active tasks and put back alarms for push
     * notification.
     */
    private static final String TAG = "DeviceRebootReceiver";


    private TaskRepository mRepository;
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        /*
         * Checking if the call is indeed from device reboot. IF it is
         * we call rescheduleActiveTasks()
         */
        if (intent.getAction() != null &&
                intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            //  getting repository for getting active tasks and
            //  notification manager for scheduling notification from the injector
            mRepository = Injection.getTaskRepository(context);
            mNotificationManager = Injection.getNotificationManager(context);
            mNotificationManager.setOpeningIntent(new Intent(context, HomeActivity.class));
            rescheduleActiveTasks();
        }

    }

    /**
     * Returns a list of active tasks from db and calls
     * schedule Alarm on it.
     */
    private void rescheduleActiveTasks() {
        Disposable disposable =
                mRepository.getAllActiveTasks()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::scheduleAlarm,
                                throwable -> Log.e(TAG, "rescheduleActiveTasks: ", throwable)
                        );

        disposable.dispose();
    }

    /**
     * Iterates over all the active tasks and calls scheduleTask over notificationManager.
     * It will schedule a push notification or ignore it if no deadline is present.
     */
    private void scheduleAlarm(List<Task> tasks) {
        for (Task task : tasks) {
            mNotificationManager.scheduleTask(task);
        }
    }
}
