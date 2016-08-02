package com.kongmy.androidprac;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SettingActivity extends ThemedActivity implements View.OnClickListener {

    private TextView textviewText;
    private TextView textviewBackground;
    private Spinner spinnerText;
    private Spinner spinnerBackground;
    private SpinnerAdapter adapter;

    public static Map<String, Integer> getColorMap() {
        HashMap<String, Integer> map = new HashMap<>();

        map.put("White", Color.WHITE);
        map.put("Red", Color.RED);
        map.put("Green", Color.GREEN);
        map.put("Blue", Color.BLUE);
        map.put("Cyan", Color.CYAN);
        map.put("Magenta", Color.MAGENTA);
        map.put("Yellow", Color.YELLOW);
        map.put("Black", Color.BLACK);

        return map;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textviewText = (TextView) findViewById(R.id.textview_textcolor);
        textviewBackground = (TextView) findViewById(R.id.textview_backgroundcolor);
        spinnerText = (Spinner) findViewById(R.id.spinner_textcolor);
        spinnerBackground = (Spinner) findViewById(R.id.spinner_backgroundcolor);

        LinkedList<String> colors = new LinkedList<>(getColorMap().keySet());
        ;
        spinnerText.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colors));
        spinnerBackground.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colors));

        spinnerText.setSelection(getColorPositionFromMap(getTextColorFromPreferences()));
        spinnerBackground.setSelection(getColorPositionFromMap(getBackgroundColorFromPreferences()));

        findViewById(R.id.btn_save).setOnClickListener(this);

        updateTheme();
    }

    private int getColorPositionFromMap(int target) {
        int pos = 0;

        for (int val : getColorMap().values()) {
            if (val == target)
                return pos;
            pos++;
        }

        return 0;
    }

    @Override
    protected void updateTextColor(int color) {
        textviewText.setTextColor(color);
        textviewBackground.setTextColor(color);
    }

    @Override
    protected void updateBackgroundColor(int color) {
        findViewById(R.id.background).setBackgroundColor(color);
    }

    @Override
    public void onClick(View v) {
        if (spinnerText.getSelectedItem().equals(spinnerBackground.getSelectedItem()))
            Toast.makeText(this, "Please avoid using same color for both", Toast.LENGTH_SHORT).show();
        else {
            Map<String, Integer> colorMap = getColorMap();
            super.setThemeColor(
                    colorMap.get(spinnerText.getSelectedItem().toString()),
                    colorMap.get(spinnerBackground.getSelectedItem().toString()));
            updateTheme();
        }
    }
}
