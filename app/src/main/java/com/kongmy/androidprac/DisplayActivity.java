package com.kongmy.androidprac;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.jamesooi.geometry.Point;

public class DisplayActivity extends AppCompatActivity {

    public static final String EXTRA_BUNDLE_MIDPOINT = "com.kongmy.androidprac.DisplayActivity.extra.bundle.midpoint";
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

        Point p = (Point) getIntent().getSerializableExtra(EXTRA_BUNDLE_MIDPOINT);
        tbxMidX.setText(String.format("%.3f", p.getX()));
        tbxMidY.setText(String.format("%.3f", p.getY()));
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
