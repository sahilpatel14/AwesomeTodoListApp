package com.ninthsemester.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.data.models.Task;

import io.reactivex.annotations.NonNull;


/**
 * Created by sahil-mac on 14/05/18.
 */

final class NotificationUtils implements NotificationInfo{

    /**
     * A static class that handles creating and deployment of
     * Notifications. It will also make call to create channels if they
     * are not already createdThis class again will be used by
     * NavigationManager( for creating a notification)
     * and NotificationPublished (for publishing it).
     */

    private static final String TAG = "NotificationUtils";

    /**
     * Creates a Channel with high importance level for Oreo and above devices.
     * For other, it ignores the request.
     */
    static void createNotificationChannel(@NonNull Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            final NotificationChannel notificationChannel =
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
                Log.d(TAG, "Notification Channel created -> "+NotificationInfo.info());
            }
        }
    }

    /**
     * Creates a notification with info from task object and returns it.
     * Uses the app name as title and task title as content. When the user clicks on
     * the notification, it opens HomeActivity.
     */
    static Notification createNotification(Context context, Task task, Intent intent) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_check)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(task.getTaskTitle());

        //  For devices older than oreo, We need to set up a max priority
        //  to get heads up notification.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
        }

        //  Creating a pending intent. Will be triggered when user clicks the notification
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //  Setting up the pending intent to open HomeActivity when user clicks on notification.
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);

        return builder.build();
    }

    /**
     * This method broadcasts the push notification for the user. It will be
     * called when Alarm Manager triggers NotificationPublisher.
     */
    static void showNotification(Context context, Notification notification) {
        createNotificationChannel(context);
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

}
