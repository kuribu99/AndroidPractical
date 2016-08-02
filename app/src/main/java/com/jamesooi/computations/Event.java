package com.jamesooi.computations;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by James Ooi on 22/7/2016.
 */
public class Event {
    private long id;
    private String title;
    private Date date;

    public Event() {
        this(0, "", Calendar.getInstance());
    }

    public Event(long id, String title, Date date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Event(long id, String title, Calendar calendar) {
        this.id = id;
        this.title = title;
        setDate(calendar);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Calendar getDateAsCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return calendar;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(Calendar calendar) {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        this.date = new Date(calendar.getTimeInMillis());
    }

    public long getCountdown() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return (today.getTimeInMillis() - date.getTime()) / (1000*60*60*24);
    }
}
