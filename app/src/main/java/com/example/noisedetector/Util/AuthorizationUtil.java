package com.example.noisedetector.Util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.example.noisedetector.NoiseDetector;
import com.example.noisedetector.R;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class AuthorizationUtil {


    private static final String TAG = AuthorizationUtil.class.getSimpleName();
    public static final String NO_TOKEN_FOUND = "no_token_found";
    public static final String NO_REFRESH_FOUND = "no_refresh_found";
    private static final String client = "trip-client";
    private static final String secret = "tripping-secret";

    public static String getToken() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NoiseDetector.getAppContext());
        String cookie = preferences.getString(NoiseDetector.getAppContext().getResources().getString(R.string.token_key), NO_TOKEN_FOUND);
        return cookie;
    }

    public static void putToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NoiseDetector.getAppContext());

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NoiseDetector.getAppContext().getResources().getString(R.string.token_key), token);
        editor.commit();
    }

    public static String getRefreshToken() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NoiseDetector.getAppContext());
        String cookie = preferences.getString(NoiseDetector.getAppContext().getResources().getString(R.string.refresh_token_key), NO_REFRESH_FOUND);

        return cookie;
    }

    public static void putRefreshToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NoiseDetector.getAppContext());

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NoiseDetector.getAppContext().getResources().getString(R.string.refresh_token_key), token);
        editor.commit();
    }

    public static String getClientId() {
        return client;
    }

    public static String getClientSecret() {
        return secret;
    }

    public static String getCredentials() {
        return getClientId() + ":" + getClientSecret();
    }

    public static String getDecodedCredentials() {
        String encodedCreds = "";
        encodedCreds = Base64.encodeToString(getCredentials().getBytes(), Base64.NO_WRAP);

        return "Basic " + encodedCreds;
    }
}
