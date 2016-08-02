package com.kongmy.androidprac;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Kong My on 1/8/2016.
 */
public class EventDatePickerDialog extends DatePickerDialog {

    public EventDatePickerDialog(Context context, final Button btn) {
        super(context,
                new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        btn.setText(String.format("%02d/%02d/%4d", dayOfMonth, monthOfYear + 1, year));
                        Calendar calendar = Calendar.getInstance();
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        // Set the minimum to today
        this.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
    }

}
