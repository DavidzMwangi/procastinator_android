package com.example.mwang.procastinator.services;

import com.example.mwang.procastinator.models.access.Authorization;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {

    @POST("login")
    @FormUrlEncoded
    Call<Authorization> tryLogin(@Field("username") String title, @Field("password") String body);


    @POST("register")
    @FormUrlEncoded
    Call<Authorization> tryRegister(@Field("email") String email, @Field("password") String password, @Field("password_confirmation") String password_confirmation);

}
