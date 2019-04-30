package com.example.monetize.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monetize.R;
import com.example.monetize.signup_and_login.LoginActivity;
import com.example.monetize.signup_and_login.SignupActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int i;
    private int dotsCount;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setIdsTextsAndListeners();
    }


    private void setIdsTextsAndListeners() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        setUpViewPagerAdapter(viewPager);
        setUpIndicators(viewPager);

        Button signup = findViewById(R.id.btnsignup_);
        signup.setOnClickListener(this);

        TextView login = findViewById(R.id.tv_login);
        SpannableString ss = new SpannableString("Already have an account? Login Here");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                //Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.WHITE);
            }
        };
        ss.setSpan(clickableSpan, 25, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        login.setText(ss);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setHighlightColor(Color.TRANSPARENT);

    }


    private void setUpViewPagerAdapter(ViewPager v) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstFragment());
        adapter.addFragment(new SecondFragment());
        adapter.addFragment(new ThirdFragment());
        adapter.addFragment(new FourthFragment());
        v.setAdapter(adapter);

        dotsCount = adapter.getCount();

    }


    private void setUpIndicators(ViewPager v) {
        LinearLayout linearLayout = findViewById(R.id.indicator);

        final ImageView imageViews[] = new ImageView[dotsCount];
        for (i = 0; i < dotsCount; i++) {
            imageViews[i] = new ImageView(this);
            imageViews[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            linearLayout.addView(imageViews[i], params);
        }

        imageViews[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        v.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int j) {

                for (i = 0; i < dotsCount; i++) {
                    imageViews[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                imageViews[j].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnsignup_) {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_down_activity, 0);
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private void addFragment(Fragment f) {
            fragmentList.add(f);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }


}
