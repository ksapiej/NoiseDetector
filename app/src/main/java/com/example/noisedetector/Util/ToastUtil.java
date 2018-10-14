package com.example.noisedetector.Util;

import android.widget.Toast;

import com.example.noisedetector.NoiseDetector;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class ToastUtil {

    public static void showToast(String message) {
        Toast.makeText(NoiseDetector.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }
}
