package com.example.noisedetector.Fragment.auth;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public interface AuthorizationView {
    void startMainActivity();

    void onRegistrationFailure(String message);

    void onSuccessFullRegistration();
}
