package com.example.noisedetector.service;

import com.example.noisedetector.Model.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Krzysiek on 14.10.2018.
 */

public interface ResultService {
    @POST("createResult")
    Call<Void> sendResult(@Body Result result);

}
