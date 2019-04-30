package com.example.monetize.signup_and_login;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelYearPicker;
import com.example.monetize.R;

import java.util.ArrayList;
import java.util.Calendar;

public class GenderFragment extends Fragment implements View.OnClickListener, WheelPicker.OnWheelChangeListener {

    private TextView tv_male;
    private TextView tv_female;
    private TextView tv_other;
    private TextView tv_select_age;
    private boolean male = false, female = false, other = false;
    private CardView cardView_male, cardView_other, cardView_female;
    private ImageView img_back;
    private View view;
    private Button finish;
    private Fragment mContext;
    private int age = 0;
    private WheelYearPicker yearPicker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_gender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.view = view;
        setIdAndListeners();
        Log.i("Monetize", "Male " + male + " Female " + female + " Other " + other);
        checkGenderAndAge();
        Log.i("Monetize", "Age onView Created" + age);
        super.onViewCreated(view, savedInstanceState);
        //createSpinnerItems();
    }


    private void setIdAndListeners() {
        cardView_male = view.findViewById(R.id.cardView_male);
        cardView_female = view.findViewById(R.id.cardView_female);
        cardView_other = view.findViewById(R.id.cardView_other);
        cardView_male.setOnClickListener(this);
        cardView_female.setOnClickListener(this);
        cardView_other.setOnClickListener(this);
        tv_male = view.findViewById(R.id.tv_male);
        tv_female = view.findViewById(R.id.tv_female);
        tv_other = view.findViewById(R.id.tv_other);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        finish = view.findViewById(R.id.finish);
        finish.setOnClickListener(this);
        yearPicker = view.findViewById(R.id.wheel_picker);
        yearPicker.setOnWheelChangeListener(this);
        yearPicker.setYearFrame(1990, 2010);
        yearPicker.setSelectedYear(2000);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cardView_male) {
            male = true;
            cardView_male.setCardBackgroundColor(getResources().getColor(R.color.color_white));
            tv_male.setTextSize(22);
            tv_male.setTextColor(getResources().getColor(R.color.black));
            if (female) {
                female = false;
                cardView_female.setCardBackgroundColor(getResources().getColor(R.color.light_green_color));
                tv_female.setTextSize(20);
                tv_female.setTextColor(getResources().getColor(R.color.color_white));
            }

            if (other) {
                other = false;
                cardView_other.setCardBackgroundColor(getResources().getColor(R.color.light_green_color));
                tv_other.setTextSize(20);
                tv_other.setTextColor(getResources().getColor(R.color.color_white));
            }
        } else if (v.getId() == R.id.cardView_female) {
            female = true;
            cardView_female.setCardBackgroundColor(getResources().getColor(R.color.color_white));
            tv_female.setTextSize(22);
            tv_female.setTextColor(getResources().getColor(R.color.black));
            if (male) {
                male = false;
                cardView_male.setCardBackgroundColor(getResources().getColor(R.color.light_green_color));
                tv_male.setTextSize(20);
                tv_male.setTextColor(getResources().getColor(R.color.color_white));
            }

            if (other) {
                other = false;
                cardView_other.setCardBackgroundColor(getResources().getColor(R.color.light_green_color));
                tv_other.setTextSize(20);
                tv_other.setTextColor(getResources().getColor(R.color.color_white));
            }
        } else if (v.getId() == R.id.cardView_other) {
            other = true;
            cardView_other.setCardBackgroundColor(getResources().getColor(R.color.color_white));
            tv_other.setTextSize(22);
            tv_other.setTextColor(getResources().getColor(R.color.black));
            if (male) {
                male = false;
                cardView_male.setCardBackgroundColor(getResources().getColor(R.color.light_green_color));
                tv_male.setTextSize(20);
                tv_male.setTextColor(getResources().getColor(R.color.color_white));
            }

            if (female) {
                female = false;
                cardView_female.setCardBackgroundColor(getResources().getColor(R.color.light_green_color));
                tv_female.setTextSize(20);
                tv_female.setTextColor(getResources().getColor(R.color.color_white));
            }
        } else if (v.getId() == R.id.img_back) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
        }

        /*else if(v.getId()==R.id.tv_select_age)
        {
            showYearDialog();
        }*/

        else if (v.getId() == R.id.finish) {
            if (male == false && female == false && other == false) {
                Toast.makeText(getContext(), "PLease choose your gender", Toast.LENGTH_SHORT).show();
            }

           /* else if(tv_select_age.getText().toString().equals("Select Age"))
            {
                Toast.makeText(getContext(),"Please select your age",Toast.LENGTH_SHORT).show();
            }*/

            else {
                Log.i("Monetize", "Age next " + age);
                Fragment f = new VerifyEmailFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, f);
                transaction.setCustomAnimations(R.anim.slide_down_fragment, 0);
                transaction.addToBackStack(null);
                manager.saveFragmentInstanceState(this);
                transaction.commit();
            }
        }

    }

    /*
    This method is used to restore the fragment state on coming back from next fragment
     */

    private void checkGenderAndAge() {
        if (age > 0) {
            // tv_select_age.setText(age);
            yearPicker.setSelectedYear(age);
            Log.i("Monetize", "Age check " + age);
        }
        if (male) {
            cardView_male.setCardBackgroundColor(getResources().getColor(R.color.color_white));
            tv_male.setTextSize(22);
            tv_male.setTextColor(getResources().getColor(R.color.black));
        } else if (female) {
            cardView_female.setCardBackgroundColor(getResources().getColor(R.color.color_white));
            tv_female.setTextSize(20);
            tv_female.setTextColor(getResources().getColor(R.color.black));
        } else if (other) {
            cardView_other.setCardBackgroundColor(getResources().getColor(R.color.color_white));
            tv_other.setTextSize(20);
            tv_other.setTextColor(getResources().getColor(R.color.black));
        }

    }


    @Override
    public void onWheelScrolled(int offset) {

    }

    @Override
    public void onWheelSelected(int position) {

        age = yearPicker.getYearStart() + position;
        Log.i("Monetize", "Wheel selected " + age);

    }

    @Override
    public void onWheelScrollStateChanged(int state) {

    }
}















































   /* public void showYearDialog()
    {

        int year= Calendar.getInstance().get(Calendar.YEAR);
        final Dialog d = new Dialog(getActivity());
        d.setTitle("Year Picker");
        d.setContentView(R.layout.date_picker);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(""+year);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(year+50);
        nopicker.setMinValue(year-50);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tv_select_age.setText(String.valueOf(nopicker.getValue()));
                age=tv_select_age.getText().toString();
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        d.show();
        d.getWindow().setLayout((6 * width)/10, LinearLayout.LayoutParams.WRAP_CONTENT);


    }*/