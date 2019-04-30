package com.example.monetize.signup_and_login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monetize.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private ImageView next;
    private FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setIdTextsAndListeners();
    }


    /*
      creating spannable string to enable some part of text clickable and coloured and also setting listener on back button, next button and
      progress bar
     */

    private void setIdTextsAndListeners() {
        TextView login = findViewById(R.id.tv_reset_password);
        username = findViewById(R.id.et_username_login);
        password = findViewById(R.id.et_password_login);
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        next = findViewById(R.id.img_next);
        next.setOnClickListener(this);
        progressBar = findViewById(R.id.progress_bar);
        SpannableString ss = new SpannableString("I forgot my password Reset here ");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Toast.makeText(LoginActivity.this, "Reset Password", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.WHITE);

            }
        };
        ss.setSpan(clickableSpan, 21, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        login.setText(ss);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setHighlightColor(Color.TRANSPARENT);
        login.setTypeface(null, Typeface.BOLD);
        login.setTextScaleX((float) 1.05);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /*
      if user has clicked on next arrow then verfying the login credentials by hitting login api .if login credentials are correct then
      displaying user details on next Activity.
      If user has clicked on back arrow then finishing the current activity.
     */
    @Override
    public void onClick(View v) {

        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(.{8,15})$";
        if (v.getId() == R.id.img_back)
            finish();

        else if (v.getId() == R.id.img_next) {
            if (username.getText().toString().length() < 6)
                Toast.makeText(this, "Please enter user name of atleast 6 characters", Toast.LENGTH_SHORT).show();

            else if (!password.getText().toString().matches(passwordRegex)) {
                String toastString = "Password should have atleast one Upper Case, atleast one digit and should have atleast 8 characters and maximum 15";
                Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
            } else {
                next.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new ReusablesAndConstants().getRetrofit(Api.LOGIN_URL);
                Api api = retrofit.create(Api.class);

                Call<UserDetails> call = api.login(username.getText().toString(), password.getText().toString());

                call.enqueue(new Callback<UserDetails>() {
                    @Override
                    public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                        if (response.code() == 401) {
                            Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                            next.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {

                            UserDetails userDetails = response.body();
                            Intent i = new Intent(LoginActivity.this, UserDetailsActivity.class);
                            i.putExtra("user_details", userDetails);
                            startActivity(i);
                            next.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<UserDetails> call, Throwable t) {

                        Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                        Log.i("Monetize", "Failure " + t.getMessage());
                        next.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }

        }

    }
}
