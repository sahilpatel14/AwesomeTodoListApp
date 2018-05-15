package com.example.awesometodolistapp.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.example.awesometodolistapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.awesometodolistapp.common.Constants.DATE_TIME_DISPLAY_FORMAT;

/**
 * Created by sahil-mac on 14/05/18.
 */

public final class Utils {

    /**
     * Checks if the passed date comes after today's date.
     */
    public static boolean isDateFromFuture(Date date) {

        Calendar curDate = Calendar.getInstance();
        return  curDate.getTimeInMillis() < date.getTime();
    }

    /**
     * Takes a date object and a format and then converts date
     * into that format and returns it as a string
     */
    public static String getDateInFormat(Date date, String format){
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     * A simple method that returns null if field is empty
     * or returns the actual input string. By default empty
     * gets converted to ""
     */
    @Nullable
    public static String getInputFromField(EditText editText) {

        String inputString = null;
        if (!editText.getText().toString().isEmpty()) {
            inputString = editText.getText().toString();
        }
        return inputString;
    }

    /**
     * Returns a simple alert dialog that will be used to show basic error messages.
     * It's up to the call to call the show() method on returned AlertDialog object
     * to display the actual dialog.
     */
    public static AlertDialog simpleAlertDialog(Context context, String message) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .create();
    }

}
