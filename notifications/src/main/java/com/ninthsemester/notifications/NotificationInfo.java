package com.ninthsemester.notifications;

/**
 * Created by sahil-mac on 14/05/18.
 */

public interface NotificationInfo {

    /**
     * Contains some constants which will be useful when creating a Notification.
     * It's good to separate out Notification constants like this. Both Notification
     * Utils and NotificationManager requires this info.
     */

    String CHANNEL_ID = "AwesomeTodoAppNotificationChannel";
    String CHANNEL_NAME = "Primary todo alert channel";
    String CHANNEL_DESCRIPTION = "This is the primary channel over which we will show our notifications";
    int NOTIFICATION_ID = 1007;

    /*
     * In case we ever have to display NotificationInfo
     */
    static String info() {
        return CHANNEL_ID+" : ("+CHANNEL_NAME+"."+CHANNEL_DESCRIPTION+").";
    }
}
