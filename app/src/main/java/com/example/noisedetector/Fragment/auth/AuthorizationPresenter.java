package com.example.noisedetector.Fragment.auth;

import android.util.Log;

import com.example.noisedetector.Listener.LoginListener;
import com.example.noisedetector.Listener.RegistrationListener;
import com.example.noisedetector.Listener.UserListener;
import com.example.noisedetector.Model.User;
import com.example.noisedetector.RetrofitConnector;
import com.example.noisedetector.Util.UserUtil;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class AuthorizationPresenter implements AuthorizationPresenterInterface, LoginListener, UserListener, RegistrationListener {

    private static final String TAG = AuthorizationPresenter.class.getSimpleName();

    private AuthorizationView view;
    private String email;

    @Override
    public void attachView(AuthorizationView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void login(String email, String password) {
        RetrofitConnector.getInstance().login(email, password, this);
        this.email = email;
    }

    @Override
    public void register(User user) {
        RetrofitConnector.getInstance().register(user, this);
    }


    @Override
    public void loginSucceeded() {
        RetrofitConnector.getInstance().getUser(this);
    }

    @Override
    public void loginFailed() {
        Log.i(TAG, "login failed");
    }


    private String trim(String cookie) {
        String trimmedCookie = cookie;

        int position = trimmedCookie.indexOf(";");
        if(position!=-1) {
            trimmedCookie = trimmedCookie.substring(0,position);
        }

        return trimmedCookie;
    }

    @Override
    public void onUserFound(User user) {
        UserUtil.setUser(user);
        Log.i(TAG, "on user found");
        view.startMainActivity();
    }

    @Override
    public void onUserNotFound() {

    }

    @Override
    public void onSuccessfullRegistration(User user) {
        view.onSuccessFullRegistration();
    }

    @Override
    public void onFailure(String message) {
        view.onRegistrationFailure(message);
    }
}
