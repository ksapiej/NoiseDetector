package com.example.noisedetector.Controller;

import android.util.Log;

import com.example.noisedetector.Listener.UserListener;
import com.example.noisedetector.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class UserController implements Callback<User> {

    private static final String TAG = UserController.class.getSimpleName();

    private UserListener userListener;


    public UserController(UserListener userListener) {
        this.userListener = userListener;
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        Log.i(TAG, "response: " + response.code());
        if(response.code() == 200) {
            userListener.onUserFound(response.body());
        }
        else userListener.onUserNotFound();
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        userListener.onUserNotFound();
    }
}
