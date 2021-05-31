package com.example.demoapi.api;

import com.example.demoapi.LoginResponse;
import com.example.demoapi.RegisterResponse;
import com.example.demoapi.model.DataModal;
import com.example.demoapi.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://ancient-fortress-14901.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("users/")
    Call<List<User>> getUsers(@Query("format") String format);

    @POST("auth/login/")
    Call<LoginResponse> loginUser(@Body DataModal dataModal);

    @POST("auth/register/")
    Call<RegisterResponse> registerUser(@Body DataModal dataModal);

}
