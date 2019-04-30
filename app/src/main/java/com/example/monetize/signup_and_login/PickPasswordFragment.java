package com.example.monetize.signup_and_login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monetize.R;

public class PickPasswordFragment extends Fragment implements View.OnClickListener {

    private EditText password;
    private ImageView img_back;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        this.view = view;
        setIdAndListeners();
        openKeyboard();
    }

    /*
       Setting ids and listeners on text view , edit text field and next and back arrow images
     */
    private void setIdAndListeners() {
        TextView tv_pickPassword = view.findViewById(R.id.tv_pickPassword);
        tv_pickPassword.setTextScaleX((float) 1.1);
        password = view.findViewById(R.id.et_pickPassword);
        ImageView next = view.findViewById(R.id.img_next);
        next.setOnClickListener(this);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }


    /*
      Opening the soft input keypad on start of current fragment
     */
    private void openKeyboard() {

        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(password, 0);
        password.requestFocus();
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /*
    Closing the soft input keypad when app is not in foreground
     */
    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
        super.onPause();
    }

    /*
    Checking if user has clicked on next arrow image then if password matches the standard password regex then opening the next fragment
    if user has clicked on back arrow then closing the current fragment
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_next) {

            String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(.{8,15})$";

            if (password.getText().toString().matches(passwordRegex)) {
                Bundle args = new Bundle();
                args.putString("password", password.getText().toString());
                Fragment f = new ConfirmPasswordFragment();
                f.setArguments(args);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, f);
                transaction.setCustomAnimations(R.anim.slide_down_fragment, 0);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                String toastString = "Password should have atleast one Upper Case, atleast one digit and should have atleast 8 characters and maximum 15";
                Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.img_back) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
        }

    }


}
