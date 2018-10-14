package com.example.noisedetector.Fragment.auth;

import com.example.noisedetector.Model.User;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public interface AuthorizationPresenterInterface {
    void attachView(AuthorizationView view);
    void detachView();

    void login(String email, String password);

    void register(User user);
}
