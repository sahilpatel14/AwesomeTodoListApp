package com.example.data.common;

import java.util.Date;
import java.util.UUID;

/**
 * Created by sahil-mac on 14/05/18.
 */

public final class DataUtils {

    /**
     * Returns a random Id that will be used as primary key for new Task objects.
     */
    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Returns current date.
     */
    public static Date getCurrentDate() {
        return new Date();
    }
}
