package com.example.noisedetector.Listener;

import com.example.noisedetector.Model.User;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public interface RegistrationListener {
    void onSuccessfullRegistration(User user);
    void onFailure(String message);
}
