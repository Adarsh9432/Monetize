package com.example.monetize.signup_and_login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    public String EMAIL_CHECK_URL = "https://api.mydatamarx.com/";
    public String USERNAME_CHECK_URL = "https://api.mydatamarx.com/";
    public String LOGIN_URL = "https://demo-api.mydatamarx.com/";


    @GET("api/v1/account/check/email")
    Call<ResponseBody> checkEmail(@Query("email") String email);

    @GET("api/v1/account/check/username")
    Call<ResponseBody> checkUserName(@Query("username") String userName);

    @FormUrlEncoded
    @POST("api/v1/login")
    Call<UserDetails> login(@Field("username") String username, @Field("password") String password);
}
