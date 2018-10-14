package com.example.noisedetector.Controller;

import com.example.noisedetector.Listener.RegistrationListener;
import com.example.noisedetector.Model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class RegistrationController implements Callback<User> {

    private RegistrationListener listener;


    public RegistrationController(RegistrationListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.code() == 200) {
            listener.onSuccessfullRegistration(response.body());
        } else {
            try {
                listener.onFailure(response.errorBody().string());
            } catch (IOException e) {
                listener.onFailure("Jakiś błąd");
            }

        }


    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        listener.onFailure("Jakiś błąd");
    }
}
