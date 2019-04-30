package com.example.monetize.signup_and_login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class ConfirmPasswordFragment extends Fragment implements View.OnClickListener {


    private EditText confirmPassword;
    private ImageView img_back;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        this.view = view;
        setIdAndListeners();
    }


    /*
      Setting ids and listeners for text views and next and back images
     */
    private void setIdAndListeners() {
        TextView tv_confirmPassword = view.findViewById(R.id.tv_confirmPassword);
        tv_confirmPassword.setTextScaleX((float) 1.1);
        confirmPassword = view.findViewById(R.id.et_confirmPassword);
        ImageView next = view.findViewById(R.id.img_next);
        next.setOnClickListener(this);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        openKeyboard();
        Log.i("Monetize", "Password " + getArguments().getString("password"));
    }

    /*
    Opening soft keypad on starting of current fragment
     */
    private void openKeyboard() {

        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(confirmPassword, 0);
        confirmPassword.requestFocus();
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /*
    closing keypad in onPause
     */
    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(confirmPassword.getWindowToken(), 0);
        super.onPause();
    }


    /*
    Checking if user has clicked for going next then opening next fragment if Password field  matches the password regex pattern
    if user has clicked for going back then popping the current fragment
     */

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_next) {
            if (confirmPassword.getText().toString().equals(getArguments().getString("password"))) {
                Fragment f = new GenderFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, f);
                transaction.setCustomAnimations(R.anim.slide_down_fragment, 0);
                transaction.addToBackStack(null);
                transaction.commit();
            } else
                Toast.makeText(getContext(), "Password did'nt match", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.img_back) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
        }
    }


}
