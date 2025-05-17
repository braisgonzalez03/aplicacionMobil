package com.example.appclubtenis.Service;

import com.example.appclubtenis.Requests.LoginRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiPlayersService {
    @POST("/authentication/login")
    Call<Map<String,String>> login(@Body LoginRequest loginRequest);
}
