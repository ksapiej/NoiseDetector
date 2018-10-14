package com.example.noisedetector.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.example.noisedetector.Activity.MainActivity;
import com.example.noisedetector.Util.Constants;

/**
 * Created by Krzysiek on 14.10.2018.
 */

public class TimerService extends Service {
    private static final String TAG = TimerService.class.getSimpleName();
    private long startTime, endTime;
    private boolean isTimerRunning;

    @Override
    public void onCreate() {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Creating service");
        }
        startTime = 0;
        endTime = 0;
        isTimerRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.START_SERVICE)) {
            Log.i(TAG, "Received Start Foreground Timer Intent ");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Timer")
                    .setTicker("Timer service")
                    .setContentIntent(pendingIntent)
                    .setOngoing(true).build();
            startForeground(Constants.NOTIFICATION.TIMER_SERVICE_ID,
                    notification);
        } else if (intent.getAction().equals(
                Constants.ACTION.STOP_SERVICE)) {
            Log.i(TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroying service");
    }

    /**
     * Starts the timer
     */
    public void startTimer() {
        if (!isTimerRunning) {
            startTime = System.currentTimeMillis();
            isTimerRunning = true;
        } else {
            Log.e(TAG, "startTimer request for an already running timer");
        }
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        if (isTimerRunning) {
            endTime = System.currentTimeMillis();
            isTimerRunning = false;
        } else {
            Log.e(TAG, "stopTimer request for a timer that isn't running");
        }
    }

    /**
     * @return whether the timer is running
     */
    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    /**
     * Returns the  elapsed time
     *
     * @return the elapsed time in seconds
     */
    public long elapsedTime() {
        // If the timer is running, the end time will be zero
        return endTime > startTime ?
                (endTime - startTime) :
                (System.currentTimeMillis() - startTime);
    }

    public String formatMilliSecondsToTime(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        return twoDigitString(minutes) + ":" + twoDigitString(seconds) + ":"
                + twoDigitString(milliseconds);
    }

    private String twoDigitString(long number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }
}
