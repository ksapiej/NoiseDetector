package com.example.noisedetector.service;

import com.example.noisedetector.OAuth.OAuthResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST("oauth/token")
    Call<OAuthResponse> login(@Field("username") String email, @Field("password") String password, @Field("grant_type") String msg, @Header("Authorization") String auth);

}
