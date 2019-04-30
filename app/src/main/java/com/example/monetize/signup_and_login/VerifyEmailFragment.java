package com.example.monetize.signup_and_login;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monetize.R;

public class VerifyEmailFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {


    boolean valOtp1 = false, valOtp2 = false, valOtp3 = false, valOtp4 = false, valOtp5 = false;
    private EditText et_otp1, et_otp2, et_otp3, et_otp4, et_otp5;
    private View view, view1, view2, view3, view4, view5;
    private ImageView otp1Dot, otp2Dot, otp3Dot, otp4Dot, otp5Dot;
    private RelativeLayout otp1Layout, otp2Layout, otp3Layout, otp4Layout, otp5Layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_email, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        this.view = view;
        setIdAndListeners();
        createSpannableTextView();
        openKeyboard();
    }

    /*
       creating some part of text view clickable with the help of spannable string
     */

    private void createSpannableTextView() {
        TextView login = view.findViewById(R.id.tv_resend_code);
        SpannableString ss = new SpannableString("Didn't get it? Resend Code ");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Toast.makeText(getContext(), "Resend Code", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.WHITE);

            }
        };
        ss.setSpan(clickableSpan, 15, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        login.setText(ss);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setHighlightColor(Color.TRANSPARENT);
        login.setTypeface(null, Typeface.BOLD);
        login.setTextScaleX((float) 1.1);
    }


    /*
     Setting ids for otp edit texts and for next and back images. Also setting text change and key change listeners on otp edit texts
     */
    private void setIdAndListeners() {
        TextView email = view.findViewById(R.id.tv_verify_email);
        email.setTextScaleX((float) 1.1);
        et_otp1 = view.findViewById(R.id.et_otp1);
        et_otp2 = view.findViewById(R.id.et_otp2);
        et_otp3 = view.findViewById(R.id.et_otp3);
        et_otp4 = view.findViewById(R.id.et_otp4);
        et_otp5 = view.findViewById(R.id.et_otp5);
        et_otp1.addTextChangedListener(new ModifiedTextWatcher(et_otp1));
        et_otp2.addTextChangedListener(new ModifiedTextWatcher(et_otp2));
        et_otp3.addTextChangedListener(new ModifiedTextWatcher(et_otp3));
        et_otp4.addTextChangedListener(new ModifiedTextWatcher(et_otp4));
        et_otp5.addTextChangedListener(new ModifiedTextWatcher(et_otp5));
        et_otp1.setOnKeyListener(this);
        et_otp2.setOnKeyListener(this);
        et_otp3.setOnKeyListener(this);
        et_otp4.setOnKeyListener(this);
        et_otp5.setOnKeyListener(this);
        ImageView img_next = view.findViewById(R.id.img_next);
        img_next.setOnClickListener(this);
        ImageView img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        otp1Dot = view.findViewById(R.id.otp1_dot);
        otp2Dot = view.findViewById(R.id.otp2_dot);
        otp3Dot = view.findViewById(R.id.otp3_dot);
        otp4Dot = view.findViewById(R.id.otp4_dot);
        otp5Dot = view.findViewById(R.id.otp5_dot);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        view4 = view.findViewById(R.id.view4);
        view5 = view.findViewById(R.id.view5);
        otp1Layout = view.findViewById(R.id.otp1_layout);
        otp2Layout = view.findViewById(R.id.otp2_layout);
        otp3Layout = view.findViewById(R.id.otp3_layout);
        otp4Layout = view.findViewById(R.id.otp4_layout);
        otp5Layout = view.findViewById(R.id.otp5_layout);
        otp1Layout.setOnClickListener(this);
        otp2Layout.setOnClickListener(this);
        otp3Layout.setOnClickListener(this);
        otp4Layout.setOnClickListener(this);
        otp5Layout.setOnClickListener(this);
    }

    /*
      Using onPause to close the keyboard when app is not in foreground
     */
    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_otp1.getWindowToken(), 0);
        super.onPause();
    }


    /*
      Method for opening keypad on start of the application
     */
    private void openKeyboard() {

        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(et_otp1, 0);
        et_otp1.requestFocus();
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_next:
                if (checkOtpField()) {
                    if (combineOtpFields().equals("12345"))
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "PLease fill all OTP fields", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_back:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();
                break;
            case R.id.otp1_layout:
                openKeyboardForOtp(et_otp1);
                Log.i("Monetize", "cursor 1st");
                if (et_otp1.getText().toString().equals(""))
                    et_otp1.requestFocus();
                else
                    et_otp1.setSelection(et_otp1.getText().length());
                break;
            case R.id.otp2_layout:
                openKeyboardForOtp(et_otp2);
                Log.i("Monetize", "cursor 2nd");
                if (et_otp2.getText().toString().equals(""))
                    et_otp2.requestFocus();
                else
                    et_otp2.setSelection(et_otp2.getText().length());
                break;
            case R.id.otp3_layout:
                openKeyboardForOtp(et_otp3);
                Log.i("Monetize", "cursor 3rd");
                if (et_otp3.getText().toString().equals(""))
                    et_otp3.requestFocus();
                else
                    et_otp3.setSelection(et_otp3.getText().length());
                break;
            case R.id.otp4_layout:
                openKeyboardForOtp(et_otp4);
                Log.i("Monetize", "cursor 4th");
                if (et_otp4.getText().toString().equals(""))
                    et_otp4.requestFocus();
                else
                    et_otp4.setSelection(et_otp4.getText().length());
                break;
            case R.id.otp5_layout:
                openKeyboardForOtp(et_otp5);
                Log.i("Monetize", "cursor 5th");
                if (et_otp5.getText().toString().equals(""))
                    et_otp5.requestFocus();
                else
                    et_otp5.setSelection(et_otp5.getText().length());
                break;
            default:
                break;

        }


    }

    private void openKeyboardForOtp(EditText editText) {

        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(editText, 0);
        editText.requestFocus();
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private String combineOtpFields() {
        String s = et_otp1.getText().toString() + et_otp2.getText().toString() + et_otp3.getText().toString() + et_otp4.getText().toString() + et_otp5.getText().toString();

        return s;
    }

    /*
      Checking if all otp edit text fields are filled or not
     */
    private boolean checkOtpField() {

        int otp1 = et_otp1.getText().toString().length();
        int otp2 = et_otp2.getText().toString().length();
        int otp3 = et_otp3.getText().toString().length();
        int otp4 = et_otp4.getText().toString().length();
        int otp5 = et_otp5.getText().toString().length();
        if (otp1 == 0 || otp2 == 0 || otp3 == 0 || otp4 == 0 || otp5 == 0)
            return false;
        else
            return true;
    }


    /*
    Checking if user has pressed delete key on soft keypad
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
            Log.i("Monetize", "on Key");

            if (v.getId() == R.id.et_otp2 && !valOtp2)
                et_otp1.setText("");
            else if (v.getId() == R.id.et_otp3 && !valOtp3)
                et_otp2.setText("");
            else if (v.getId() == R.id.et_otp4 && !valOtp4)
                et_otp3.setText("");
            else if (v.getId() == R.id.et_otp5 && !valOtp5)
                et_otp4.setText("");
            else if (v.getId() == R.id.et_otp5 && valOtp5)
                et_otp5.setText("");

        }

        return false;
    }



    /*
        Modified Text Watcher class to auto manage the cursors for otp fields
     */

    class ModifiedTextWatcher implements TextWatcher {

        private View view;


        ModifiedTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {


            String text = s.toString();
            switch (view.getId()) {

                case R.id.et_otp1:
                    if (text.length() == 1) {
                        otp1Dot.setVisibility(View.VISIBLE);
                        view1.setVisibility(View.GONE);
                        et_otp2.requestFocus();
                        valOtp1 = true;
                    } else if (text.length() == 0) {
                        view1.setVisibility(View.VISIBLE);
                        otp1Dot.setVisibility(View.GONE);
                        et_otp1.requestFocus();
                        valOtp1 = false;
                    }
                    break;

                case R.id.et_otp2:
                    if (text.length() == 1) {
                        otp2Dot.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.GONE);
                        et_otp3.requestFocus();
                        valOtp2 = true;
                    } else if (text.length() == 0) {
                        view2.setVisibility(View.VISIBLE);
                        otp2Dot.setVisibility(View.GONE);
                        et_otp2.requestFocus();
                        valOtp2 = false;
                    }
                    break;

                case R.id.et_otp3:
                    if (text.length() == 1) {
                        otp3Dot.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.GONE);
                        et_otp4.requestFocus();
                        valOtp3 = true;
                    } else if (text.length() == 0) {
                        view3.setVisibility(View.VISIBLE);
                        otp3Dot.setVisibility(View.GONE);
                        et_otp3.requestFocus();
                        valOtp3 = false;
                    }
                    break;

                case R.id.et_otp4:
                    if (text.length() == 1) {
                        otp4Dot.setVisibility(View.VISIBLE);
                        view4.setVisibility(View.GONE);
                        et_otp5.requestFocus();
                        valOtp4 = true;
                    } else if (text.length() == 0) {
                        view4.setVisibility(View.VISIBLE);
                        otp4Dot.setVisibility(View.GONE);
                        et_otp4.requestFocus();
                        valOtp4 = false;
                    }
                    break;

                case R.id.et_otp5:
                    if (text.length() == 1) {
                        view5.setVisibility(View.GONE);
                        otp5Dot.setVisibility(View.VISIBLE);
                        valOtp5 = true;
                    } else if (text.length() == 0) {
                        view5.setVisibility(View.VISIBLE);
                        otp5Dot.setVisibility(View.GONE);
                        et_otp5.requestFocus();
                        valOtp5 = false;
                    }
                    break;
            }

        }
    }


}
