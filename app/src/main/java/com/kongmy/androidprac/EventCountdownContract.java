package com.kongmy.androidprac;

import android.provider.BaseColumns;

/**
 * Created by Kong My on 1/8/2016.
 */
public class EventCountdownContract {

    public EventCountdownContract() {
        // Empty constructor
    }

    public static abstract class EventColumns implements BaseColumns {
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_INSERT_TIMESTAMP = "insert_timestamp";
        public static final String COLUMN_NAME_DATE_TIMESTAMP = "date_timestamp";
        public static final String COLUMN_NAME_NULLABLE = "";
    }

}
