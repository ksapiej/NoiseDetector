package com.example.noisedetector;

import android.app.Application;
import android.content.Context;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class NoiseDetector extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {return context;}
}
