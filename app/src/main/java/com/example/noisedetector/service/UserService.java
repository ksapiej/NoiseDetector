package com.example.noisedetector.service;

import com.example.noisedetector.Model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public interface UserService {
    @GET("api/r_user/user/getCurrentUser")
    Call<User> getUser();

    @FormUrlEncoded
    @POST("registration")
    Call<User> registerUser(@Field("email") String email, @Field("password") String password, @Field("userName") String username, @Header("Authorization") String auth);
}
