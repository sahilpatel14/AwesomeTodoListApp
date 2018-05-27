package com.ninthsemester.notifications;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by sahil-mac on 14/05/18.
 */

public class NotificationPublisher extends BroadcastReceiver {

    /**
     * AlarmManager calls this broadcast receiver when its time
     * to show the notification. We also receive the notification
     * that we need to show to user.
     * We use NotificationUtils to broadcast the notification.
     */

    private static final String TAG = "NotificationPublisher";

    //  NotificationManager will use this key to send notification object.
    public static final String KEY_NOTIFICATION = "todo_notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: ");

        //  Checking if we have notification object in intent.
        if (intent.hasExtra(KEY_NOTIFICATION)) {
            Notification notification = intent.getParcelableExtra(KEY_NOTIFICATION);
            showNotification(context, notification);
        }
        else {
            Log.e(TAG, "Did not find notification for showing to user.");
        }
    }

    /**
     * Using NotificationUtils to show the notification.
     */
    private void showNotification(Context context, Notification notification) {

        Log.d(TAG, "showNotification: ");
        NotificationUtils.createNotificationChannel(context);
        NotificationUtils.showNotification(context, notification);
    }
}
