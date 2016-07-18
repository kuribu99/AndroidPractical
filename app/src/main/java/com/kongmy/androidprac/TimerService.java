package com.kongmy.androidprac;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kong My on 18/7/2016.
 */
public class TimerService extends IntentService {

    public static final String EXTRA_ACTION = "com.kongmy.androidPrac.TimerService.extra.ACTION";

    public static final int ACTION_UNDEFINED = -1;
    public static final int ACTION_START = 0;
    public static final int ACTION_STOP = 1;


    private static int notificationID = 0;
    private Timer timer;

    public TimerService() {
        super("com.kongmy.androidPrac.TimerService");
    }

    public static Intent newIntent(Context context) {
        return newIntent(context, ACTION_UNDEFINED);
    }

    public static Intent newStartServiceIntent(Context context) {
        return newIntent(context, ACTION_START);
    }

    public static Intent newStopServiceIntent(Context context) {
        return newIntent(context, ACTION_STOP);
    }

    private static Intent newIntent(Context context, int action) {
        Intent intent = new Intent(context, TimerService.class);
        intent.putExtra(EXTRA_ACTION, action);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        switch (intent.getIntExtra(EXTRA_ACTION, ACTION_UNDEFINED)) {

            case ACTION_START:
                StartTimer();
                break;

            case ACTION_STOP:
                StopTimer();
                break;

        }

    }

    public void StartTimer() {
        StopTimer();
        timer = new Timer();
        timer.scheduleAtFixedRate(new Task(), 1000, 5000);
    }

    public void StopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    private class Task extends TimerTask {

        @Override
        public void run() {
            Date date = new Date();
            if (new GregorianCalendar().get(Calendar.SECOND) % 30 == 0) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(notificationID++, getNotification());
            }
        }

        private Notification getNotification() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());

            return new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_alarm_multiple_black_36dp)
                    .setContentTitle(currentDateTime)
                    .build();
        }

    }

}
