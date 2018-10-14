package com.example.noisedetector.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.noisedetector.Activity.MainActivity;
import com.example.noisedetector.Fragment.main.MainFragment;

import java.lang.ref.WeakReference;

/**
 * Created by Krzysiek on 14.10.2018.
 */

public class UIUpdateHandler extends Handler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int UPDATE_RATE_MS = 1000;
    private final WeakReference<MainFragment> fragment;

    public UIUpdateHandler(MainFragment fragment) {
        this.fragment = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message message) {
        if (Constants.NOTIFICATION.MSG_UPDATE_TIME == message.what) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "updating time");
            }
            fragment.get().updateUITimer();
            sendEmptyMessageDelayed(Constants.NOTIFICATION.MSG_UPDATE_TIME, UPDATE_RATE_MS);
        }
    }
}
