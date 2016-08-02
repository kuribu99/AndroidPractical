package com.kongmy.androidprac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jamesooi.computations.Event;

import java.util.Calendar;

public class AddEventActivity extends ThemedActivity {

    private EditText editTextTitle;
    private Button btnEditDate;
    private Button btnAdd;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddEventActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextTitle = (EditText) findViewById(R.id.textbox_title);
        btnEditDate = (Button) findViewById(R.id.btn_date);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EventDatePickerDialog(AddEventActivity.this, btnEditDate).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String date = btnEditDate.getText().toString();

                if (title.isEmpty())
                    Toast.makeText(AddEventActivity.this, "Title must not be empty", Toast.LENGTH_SHORT).show();

                else if (date.isEmpty())
                    Toast.makeText(AddEventActivity.this, "Date must not be empty", Toast.LENGTH_SHORT).show();

                else {
                    Event event = new Event();
                    event.setTitle(title);

                    Calendar calendar = Calendar.getInstance();
                    String[] dateArray = btnEditDate.getText().toString().split("/");
                    calendar.set(
                            Integer.parseInt(dateArray[2]),
                            Integer.parseInt(dateArray[1]) - 1,
                            Integer.parseInt(dateArray[0]));

                    event.setDate(calendar);
                    DatabaseHelper databaseHelper = new DatabaseHelper(AddEventActivity.this);
                    databaseHelper.saveEvent(event);

                    Toast.makeText(AddEventActivity.this, "Event saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        updateTheme();
    }

    private void showDismissibleSnackbar(String s) {
        final Snackbar snackbar = Snackbar.make(btnAdd, s, Snackbar.LENGTH_LONG);

        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    protected void updateTextColor(int color) {
        ((TextView) findViewById(R.id.textview_title)).setTextColor(color);
        ((TextView) findViewById(R.id.textview_date)).setTextColor(color);
    }

    @Override
    protected void updateBackgroundColor(int color) {
        findViewById(R.id.background).setBackgroundColor(color);
    }
}
