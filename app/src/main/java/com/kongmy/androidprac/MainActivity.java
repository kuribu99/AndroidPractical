package com.kongmy.androidprac;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startService(TimerService.newIntent(this));

        ImageButton btnStart = (ImageButton) findViewById(R.id.btn_start);
        ImageButton btnStop = (ImageButton) findViewById(R.id.btn_stop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(TimerService.newStartServiceIntent(getApplicationContext()));
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDismissibleSnackbar("Can't stop me now");
                //startService(TimerService.newStopServiceIntent(getApplicationContext()));
            }
        });
    }

    private void showDismissibleSnackbar(String s) {
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.toolbar), s, Snackbar.LENGTH_LONG);

        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        String text = "";
        switch (id) {
            case R.id.action_add:
                text = "Add action";
                break;

            case R.id.action_subtract:
                text = "Subtract action";
                break;

            case R.id.action_multiply:
                text = "Multiply action";
                break;

            case R.id.action_divide:
                text = "Divide action";
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        showDismissibleSnackbar(text);
        return true;
    }

}
