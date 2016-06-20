package com.kongmy.androidprac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jamesooi.geometry.Line;
import com.jamesooi.geometry.Point;

public class MainActivity extends AppCompatActivity {

    private EditText tbxP1X;
    private EditText tbxP1Y;
    private EditText tbxP2X;
    private EditText tbxP2Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tbxP1X = (EditText) findViewById(R.id.tbx_p1_x);
        tbxP1Y = (EditText) findViewById(R.id.tbx_p1_y);
        tbxP2X = (EditText) findViewById(R.id.tbx_p2_x);
        tbxP2Y = (EditText) findViewById(R.id.tbx_p2_y);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       if (tbxP1X.getText().toString().isEmpty()
                                               || tbxP1Y.getText().toString().isEmpty()
                                               || tbxP2X.getText().toString().isEmpty()
                                               || tbxP1Y.getText().toString().isEmpty()) {


                                           showDismissibleSnackbar("Please fill up all the coordinates for point 1 and point 2");

                                       } else {
                                           try {
                                               Point p1 = new Point(
                                                       Double.parseDouble(tbxP1X.getText().toString()),
                                                       Double.parseDouble(tbxP1Y.getText().toString()));

                                               Point p2 = new Point(
                                                       Double.parseDouble(tbxP2X.getText().toString()),
                                                       Double.parseDouble(tbxP2Y.getText().toString()));

                                               Line line = new Line();
                                               line.setP1(p1);
                                               line.setP2(p2);

                                               Point mid = line.getMidPoint();
                                               startDisplayActivity(mid);

                                           } catch (NumberFormatException e) {
                                               showDismissibleSnackbar("Please fill in only numbers for point 1 and point 2");
                                           }
                                       }
                                   }
                               }
        );
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

    private void startDisplayActivity(Point mid) {
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra(DisplayActivity.EXTRA_BUNDLE_MIDPOINT, mid);
        startActivity(intent);
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
