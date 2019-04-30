package com.example.monetize.signup_and_login;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReusablesAndConstants {

    public Retrofit getRetrofit(String s) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(s)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
