package com.example.monetize.signup_and_login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monetize.R;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setIdsTextsAndListeners();
    }


    /*
      Registering the text views and modifying the text views to show user details
     */
    private void setIdsTextsAndListeners() {

        ImageView back = findViewById(R.id.img_back);
        back.setOnClickListener(this);
        TextView firstName = findViewById(R.id.tv_first_name);
        TextView lastName = findViewById(R.id.tv_last_name);
        TextView email = findViewById(R.id.tv_email);
        TextView userName = findViewById(R.id.tv_user_name);
        TextView phone = findViewById(R.id.tv_phone);

        UserDetails userDetails = (UserDetails) getIntent().getSerializableExtra("user_details");
        firstName.setText(firstName.getText().toString() + " " + userDetails.responseData.firstName);
        lastName.setText(lastName.getText().toString() + " " + userDetails.responseData.lastName);
        email.setText(email.getText().toString() + " " + userDetails.responseData.email);
        userName.setText(userName.getText().toString() + " " + userDetails.responseData.username);
        phone.setText(phone.getText().toString() + " " + userDetails.responseData.phone);

    }

    /*
    if user has clicked on back arrow image then finishing the current activity
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back)
            finish();
    }
}
