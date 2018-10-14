package com.example.noisedetector.Util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.noisedetector.Model.User;
import com.example.noisedetector.NoiseDetector;
import com.example.noisedetector.R;

import java.util.List;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class UserUtil {
    private static final String TAG = UserUtil.class.getSimpleName();
    private static User user=null;
    public static final String NO_USER_SET = "no_user_set";

    public static void setUser(User newUser) {
        user = newUser;
        putUserIDToSharedPrefs(user.getEmail());
    }

    public static void putUserIDToSharedPrefs(String id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NoiseDetector.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NoiseDetector.getAppContext().getResources().getString(R.string.email_key), id);
        editor.commit();
    }

    public static User getUser() {
        return user;
    }

    public static String getUserId() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NoiseDetector.getAppContext());
        String email = preferences.getString(NoiseDetector.getAppContext().getResources().getString(R.string.email_key), NO_USER_SET);

        return email;
    }

}
