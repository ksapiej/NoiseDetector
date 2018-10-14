package com.example.noisedetector.OAuth;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public interface Session {
    boolean isLoggedIn();
    void saveToken(String token);
    String getToken();
    void saveRefreshToken(String token);
    String getRefreshToken();
    void invalidate();
}
