package com.kongmy.androidprac;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kong My on 2/8/2016.
 */
public abstract class ThemedActivity extends AppCompatActivity {

    protected static final String SHARED_PREFERENCES_NAME = " com.kongmy.androidprac.ThemedActivity.SharedPreferenceName";
    protected static final String SHARED_PREFERENCE_TEXT_COLOR = " com.kongmy.androidprac.ThemedActivity.TextColor";
    protected static final String SHARED_PREFERENCES_BACKGROUND_COLOR = " com.kongmy.androidprac.ThemedActivity.BackgroundColor";

    protected void setThemeColor(int textColor, int backgroundColor) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(SHARED_PREFERENCE_TEXT_COLOR, textColor)
                .putInt(SHARED_PREFERENCES_BACKGROUND_COLOR, backgroundColor)
                .apply();
    }

    protected int getTextColorFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SHARED_PREFERENCE_TEXT_COLOR, Color.GRAY);
    }

    protected int getBackgroundColorFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SHARED_PREFERENCES_BACKGROUND_COLOR, Color.GRAY);
    }

    protected void updateTheme() {
        updateTextColor(getTextColorFromPreferences());
        updateBackgroundColor(getBackgroundColorFromPreferences());
    }

    protected abstract void updateTextColor(int color);

    protected abstract void updateBackgroundColor(int color);

}
