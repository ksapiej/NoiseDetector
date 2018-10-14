package com.example.noisedetector;

import android.os.Handler;

import com.example.noisedetector.Controller.LoginController;
import com.example.noisedetector.Controller.RegistrationController;
import com.example.noisedetector.Controller.UserController;
import com.example.noisedetector.Listener.LoginListener;
import com.example.noisedetector.Listener.RegistrationListener;
import com.example.noisedetector.Listener.UserListener;
import com.example.noisedetector.Model.User;
import com.example.noisedetector.OAuth.OAuthResponse;
import com.example.noisedetector.OAuth.OAuthSession;
import com.example.noisedetector.OAuth.TokenInterceptor;
import com.example.noisedetector.Util.AuthorizationUtil;
import com.example.noisedetector.service.LoginService;
import com.example.noisedetector.service.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class RetrofitConnector {

    private static final String TAG = RetrofitConnector.class.getSimpleName();
    private static RetrofitConnector instance;
    private Retrofit retrofit;
    private LoginService loginService;
    private UserService userService;

    private RetrofitConnector() {
        initializeRetrofit();
        createServices();
    }

    public static RetrofitConnector getInstance() {
        if (instance == null) instance = new RetrofitConnector();
        return instance;
    }

    private void initializeRetrofit() {
        retrofit = getRetrofitBuilder().build();
    }

    private Retrofit.Builder getRetrofitBuilder() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .addInterceptor(new TokenInterceptor(new OAuthSession(null)))
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("http://172.28.113.68:8086/");
    }

    private void createServices() {
        loginService = retrofit.create(LoginService.class);
        userService = retrofit.create(UserService.class);
    }

    public void login(final String email, final String password, final LoginListener listener) {
        Handler handler = new Handler();
        handler.post(() -> {
            final Call<OAuthResponse> call = loginService.login(email, password, "password", AuthorizationUtil.getDecodedCredentials());
            call.enqueue(new LoginController(listener));
        });
    }

    public void register(User user, final RegistrationListener listener) {
        Handler handler = new Handler();
        handler.post(() -> {
            final Call<User> call = userService.registerUser(user.getEmail(), user.getPassword(), user.getUserName(), AuthorizationUtil.getDecodedCredentials());
            call.enqueue(new RegistrationController(listener));
        });
    }

    public void getUser(final UserListener listener) {
        Handler handler = new Handler();
        handler.post(() -> {
            final Call<User> call = userService.getUser();
            call.enqueue(new UserController(listener));
        });
    }
}
