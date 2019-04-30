package com.example.monetize.signup_and_login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monetize.R;

public class NumberFragment extends Fragment implements View.OnClickListener, TextWatcher {

    EditText et_number, et_country_code;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        this.view = view;
        setIdAndListeners();
        openKeyboard();
    }

    /*
      Setting ids and listeners for text view, edit text field and next and back arrows
     */
    private void setIdAndListeners() {
        TextView number = view.findViewById(R.id.tv_number);
        number.setTextScaleX((float) 1.1);
        et_number = view.findViewById(R.id.et_number);
        ImageView img_next = view.findViewById(R.id.img_next);
        img_next.setOnClickListener(this);
        ImageView img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        et_country_code = view.findViewById(R.id.country_code);
        et_country_code.addTextChangedListener(this);
    }

    /*
      Closing the keypad on when app is not in foreground
     */

    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_number.getWindowToken(), 0);
        super.onPause();
    }

    /*
     Opening the soft input keypad when current fragment gets started
     */

    private void openKeyboard() {

        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(et_country_code, 0);
        et_country_code.requestFocus();
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /*
     Checking if user has clicked on next arrow then moving to next fragment if 10 digit number is entered by user
     if user has clicked on back arrow then closing the current fragment
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_next) {
            if (et_number.getText().toString().length() < 10 || et_country_code.getText().toString().length() < 3)
                Toast.makeText(getContext(), "Please enter proper mobile number", Toast.LENGTH_SHORT).show();


            else {
                Fragment f = new UserNameFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, f);
                transaction.setCustomAnimations(R.anim.slide_down_fragment, 0);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        } else if (v.getId() == R.id.img_back) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String country_code = s.toString();
        ;
        if (s.length() == 1) {

            char c = country_code.charAt(0);
            if (c != '+') {
                Toast.makeText(getActivity(), "Illegal character. Country code should appear like this +91", Toast.LENGTH_SHORT).show();
                et_country_code.setText("");
            }
        } else if (country_code.length() == 3)
            et_number.requestFocus();


    }
}
