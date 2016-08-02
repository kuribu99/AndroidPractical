package com.kongmy.androidprac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jamesooi.computations.Event;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.kongmy.androidprac.EventCountdownContract.EventColumns;

/**
 * Created by Kong My on 1/8/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE_EVENT = "CREATE TABLE " + EventColumns.TABLE_NAME + " (" +
            EventColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EventColumns.COLUMN_NAME_TITLE + " VARCHAR(50) NOT NULL," +
            EventColumns.COLUMN_NAME_DATE_TIMESTAMP + " BIGINT NOT NULL" +
            ")";
    private static final String SQL_DROP_TABLE_EVENT = "DROP TABLE " + EventColumns.TABLE_NAME;
    private static final String SQL_WHERE_BY_ID = EventColumns._ID + "=?";
    private static final String DB_NAME = "EventCountdown.db";
    private static final int DB_VERSION = 1;
    private static final int NEW_EVENT_ID = 0;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_EVENT);
        onCreate(db);
    }

    public Event findEventById(long id) {
        Event event = null;
        Cursor cursor = getReadableDatabase().query(EventColumns.TABLE_NAME, null, SQL_WHERE_BY_ID, new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            event = new Event();

            event.setId(cursor.getLong(cursor.getColumnIndex(EventColumns._ID)));
            event.setTitle(cursor.getString(cursor.getColumnIndex(EventColumns.COLUMN_NAME_TITLE)));
            event.setDate(new Date(cursor.getLong(cursor.getColumnIndex(EventColumns.COLUMN_NAME_DATE_TIMESTAMP))));
        }

        return event;
    }

    public List<Event> findAllEvents() {
        List<Event> eventList = new LinkedList<>();
        Event event = null;
        Cursor cursor = getReadableDatabase().query(EventColumns.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            event = new Event();

            event.setId(cursor.getLong(cursor.getColumnIndex(EventColumns._ID)));
            event.setTitle(cursor.getString(cursor.getColumnIndex(EventColumns.COLUMN_NAME_TITLE)));
            event.setDate(new Date(cursor.getLong(cursor.getColumnIndex(EventColumns.COLUMN_NAME_DATE_TIMESTAMP))));

            eventList.add(event);
        }

        return eventList;
    }

    public void saveEvent(Event event) {
        ContentValues values = new ContentValues();
        values.put(EventColumns.COLUMN_NAME_TITLE, event.getTitle());
        values.put(EventColumns.COLUMN_NAME_DATE_TIMESTAMP, event.getDate().getTime());

        if (event.getId() == NEW_EVENT_ID) {
            long id = getWritableDatabase().insert(
                    EventColumns.TABLE_NAME,
                    null,
                    values);

            event.setId(id);
        } else {
            getWritableDatabase().update(
                    EventColumns.TABLE_NAME,
                    values,
                    SQL_WHERE_BY_ID,
                    new String[]{String.valueOf(event.getId())});
        }
    }

    public void deleteEvent(long id) {
        getWritableDatabase().delete(
                EventColumns.TABLE_NAME,
                SQL_WHERE_BY_ID,
                new String[]{String.valueOf(id)});
    }
}
