package com.kongmy.androidprac;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.jamesooi.geometry.Line;
import com.jamesooi.geometry.Point;

public class DisplayActivity extends AppCompatActivity {

    public static final String EXTRA_BUNDLE_POINT_1 = "com.kongmy.androidprac.DisplayActivity.extra.bundle.point_1";
    public static final String EXTRA_BUNDLE_POINT_2 = "com.kongmy.androidprac.DisplayActivity.extra.bundle.point_2";
    private EditText tbxMidX;
    private EditText tbxMidY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tbxMidX = (EditText) findViewById(R.id.tbx_mid_x);
        tbxMidY = (EditText) findViewById(R.id.tbx_mid_y);

        Bundle bundle = (Bundle) getIntent().getExtras();
        Point p1 = (Point) bundle.getSerializable(EXTRA_BUNDLE_POINT_1);
        Point p2 = (Point) bundle.getSerializable(EXTRA_BUNDLE_POINT_2);
        Line line = new Line();
        line.setP1(p1);
        line.setP2(p2);
        Point mid = line.getMidPoint();

        tbxMidX.setText(String.format("%.3f", mid.getX()));
        tbxMidY.setText(String.format("%.3f", mid.getY()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
