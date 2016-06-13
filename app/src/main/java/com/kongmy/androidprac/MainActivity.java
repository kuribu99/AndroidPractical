package com.kongmy.androidprac;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jamesooi.geometry.Line;
import com.jamesooi.geometry.Point;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText tbxP1X;
    private TextInputEditText tbxP1Y;
    private TextInputEditText tbxP2X;
    private TextInputEditText tbxP2Y;
    private TextInputEditText tbxMidX;
    private TextInputEditText tbxMidY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tbxP1X = (TextInputEditText) findViewById(R.id.tbx_p1_x);
        tbxP1Y = (TextInputEditText) findViewById(R.id.tbx_p1_y);
        tbxP2X = (TextInputEditText) findViewById(R.id.tbx_p2_x);
        tbxP2Y = (TextInputEditText) findViewById(R.id.tbx_p2_y);
        tbxMidX = (TextInputEditText) findViewById(R.id.tbx_mid_x);
        tbxMidY = (TextInputEditText) findViewById(R.id.tbx_mid_y);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       if (tbxP1X.getText().toString().isEmpty()
                                               || tbxP1Y.getText().toString().isEmpty()
                                               || tbxP2X.getText().toString().isEmpty()
                                               || tbxP1Y.getText().toString().isEmpty()) {

                                           tbxMidX.setText("-");
                                           tbxMidY.setText("-");

                                           final Snackbar snackbar = Snackbar.make(
                                                   view,
                                                   "Please fill up all the coordinates for point 1 and point 2",
                                                   Snackbar.LENGTH_LONG);

                                           snackbar.setAction("Dismiss", new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   snackbar.dismiss();
                                               }
                                           });
                                           snackbar.show();

                                       } else {
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
                                           tbxMidX.setText(String.format("%.3f", mid.getX()));
                                           tbxMidY.setText(String.format("%.3f", mid.getY()));

                                           final Snackbar snackbar = Snackbar.make(
                                                   view,
                                                   "Midpoint coordinates updated",
                                                   Snackbar.LENGTH_LONG);
                                           snackbar.setAction("Dismiss", new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   snackbar.dismiss();
                                               }
                                           });
                                           snackbar.show();
                                       }
                                   }
                               }
        );
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
