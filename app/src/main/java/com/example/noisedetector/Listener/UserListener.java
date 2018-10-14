package com.example.noisedetector.Listener;

import com.example.noisedetector.Model.User;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public interface UserListener {
    void onUserFound(User user);
    void onUserNotFound();
}
