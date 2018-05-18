package com.example.awesometodolistapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.example.awesometodolistapp.common.Utils;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by sahil-mac on 17/05/18.
 */

@RunWith(AndroidJUnit4.class)
public class UtilsInstrumentedTest {


    @Test(expected = IllegalArgumentException.class)
    public void getInputFromField_passingNull_throwsException() {
        Utils.getInputFromField(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInputFromField_passingView_throwsException() {
        View view = new View(InstrumentationRegistry.getContext());
        Utils.getInputFromField((EditText)view);
    }

    @Test
    public void getInputFromField_passingEditText_returnsString() {
        String input = "My input";
        EditText editText = new EditText(InstrumentationRegistry.getContext());
        editText.setText(input);
        String response = Utils.getInputFromField(editText);
        assertThat(response, is(input));
    }

    @Test
    public void getInputFromField_passingEditTextEmptyInput_returnString() {
        String input = "";
        EditText editText = new EditText(InstrumentationRegistry.getContext());
        editText.setText(input);
        String response = Utils.getInputFromField(editText);
        assertNull(response);
    }

    @Test
    public void getInputFromField_passingEditTextNullInput_returnString() {
        String input = null;
        EditText editText = new EditText(InstrumentationRegistry.getContext());
        editText.setText(input);
        String response = Utils.getInputFromField(editText);
        assertNull(response);
    }


    @Test(expected = IllegalArgumentException.class)
    public void simpleAlertDialog_passingNull_throwsException() {
        Utils.simpleAlertDialog(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void simpleAlertDialog_passingEmptyString_throwsException() {
        Utils.simpleAlertDialog(InstrumentationRegistry.getContext(), "");
    }

    @Test
    public void simpleAlertDialog_passingValidString_throwsException() {
        String message = "This is a test message";
        AlertDialog alertDialog = Utils.simpleAlertDialog(InstrumentationRegistry.getContext(), message);
        assertNotNull(alertDialog);
    }

}
