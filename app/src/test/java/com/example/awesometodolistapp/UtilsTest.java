package com.example.awesometodolistapp;

import com.example.awesometodolistapp.common.Utils;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Date;

/**
 * Created by sahil-mac on 17/05/18.
 */

public class UtilsTest {

    private static final long sevenDaysInMillis = 1000 * 60 * 60 * 24 * 7;
    public static final long oneHourInMillis = 1000 * 60 * 60;

    @Test(expected = IllegalArgumentException.class)
    public void isDateInFuture_passingNull_throwsException() {
        boolean inFuture = Utils.isDateFromFuture(null);
    }

    @Test
    public void isDateInFuture_passingOldDate_returnsFalse() {
        Date sevenDaysOldDate = new Date(System.currentTimeMillis() - sevenDaysInMillis);
        boolean inFuture = Utils.isDateFromFuture(sevenDaysOldDate);
        assertEquals(inFuture, false);
    }

    @Test
    public void isDateInFuture_passingFutureDate_returnsTrue() {
        Date sevenDaysPriorDate = new Date(System.currentTimeMillis() + sevenDaysInMillis);
        boolean inFuture = Utils.isDateFromFuture(sevenDaysPriorDate);
        assertEquals(inFuture, true);
    }

    @Test
    public void isDateInFuture_passingCurrentDate_returnsFalse() {
        Date currentDate = new Date();
        boolean inFuture = Utils.isDateFromFuture(currentDate);
        assertEquals(inFuture, false);
    }

    @Test
    public void isDateInFuture_passingCurrentDateOneHourInFuture_returnsTrue() {
        Date onHourAheadDate = new Date(System.currentTimeMillis() + oneHourInMillis);
        boolean inFuture = Utils.isDateFromFuture(onHourAheadDate);
        assertEquals(inFuture, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDateInFormat_passingNull_throwsException(){
        Utils.getDateInFormat(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDateInFormat_passingEmptyString_throwsException() {
        Utils.getDateInFormat(new Date(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDateInFormat_passingInvalidFormat_throwsException() {
        String format = "this is invalid date format";
        Utils.getDateInFormat(new Date(), format);
    }

    @Test
    public void getDateInFormat_passingValidFormat_returnsTrue() {
        String format = "d MMM";

        Date date = new Date();
        String response = Utils.getDateInFormat(date, format);
        assertEquals(response.getClass(), String.class);
    }
}
