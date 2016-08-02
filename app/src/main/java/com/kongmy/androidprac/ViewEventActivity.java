package com.kongmy.androidprac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jamesooi.computations.Event;

import java.util.Calendar;

public class ViewEventActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_ID = "com.kongmy.androidprac.ViewEventActivity.extra.eventID";

    public static Intent newIntent(Context context, long eventID) {
        Intent intent = new Intent(context, ViewEventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get event id from intent
        long eventID = getIntent().getLongExtra(EXTRA_EVENT_ID, -1);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Event event = databaseHelper.findEventById(eventID);

        if (eventID > 0 && event != null) {

            TextView textboxTitle = (TextView) findViewById(R.id.textview_title);
            TextView textboxDate = (TextView) findViewById(R.id.textview_date);
            TextView textboxCountdown = (TextView) findViewById(R.id.textview_countdown);

            Calendar eventDate = event.getDateAsCalendar();
            String date = String.format(
                    "%02d/%02d/%4d",
                    eventDate.get(Calendar.DAY_OF_MONTH),
                    eventDate.get(Calendar.MONTH) + 1,
                    eventDate.get(Calendar.YEAR));

            int days = (int) event.getCountdown();
            String countdown = String.valueOf(Math.abs(days)) + (Math.abs(days) == 1 ? " day " : " days ");

            // Already finished countdown
            if(days >= 0)
                countdown += "passed";
            else {
                countdown+= "ahead";
            }

            textboxTitle.setText(event.getTitle());
            textboxDate.setText(date);
            textboxCountdown.setText(countdown);
        }
        // End the activity event id not found
        else {
            Toast.makeText(this, "Invalid event ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
