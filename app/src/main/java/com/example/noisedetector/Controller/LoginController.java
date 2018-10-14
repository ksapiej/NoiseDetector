package com.example.noisedetector.Controller;

import android.util.Log;

import com.example.noisedetector.Listener.LoginListener;
import com.example.noisedetector.OAuth.OAuthResponse;
import com.example.noisedetector.Util.AuthorizationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class LoginController implements Callback<OAuthResponse> {


    private static final String TAG = LoginController.class.getSimpleName();

    private LoginListener loginListener;


    public LoginController(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public void onResponse(Call<OAuthResponse> call, Response<OAuthResponse> response) {
        try {

            Log.i(TAG, "code: " + response.code());

            if(response.code()==200) {
                OAuthResponse oAuthResponse = response.body();
                AuthorizationUtil.putRefreshToken(oAuthResponse.getRefresh_token());
                AuthorizationUtil.putToken(oAuthResponse.getAccess_token());
                Log.i(TAG, AuthorizationUtil.getRefreshToken());
                Log.i(TAG, AuthorizationUtil.getToken());
                loginListener.loginSucceeded();
            }
            else loginListener.loginFailed();



        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<OAuthResponse> call, Throwable t) {
        Log.i(TAG, "login failure: ");
        Log.e(TAG, t.toString());
        loginListener.loginFailed();

    }
}
