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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monetize.R;

public class LastNameFragment extends Fragment implements View.OnClickListener, TextWatcher {


    private EditText editText;
    private ImageView img_back;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_name, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        this.view = view;
        setIdAndListeners();
        editText.requestFocus();
        openKeyboard();
    }

    /*
    Setting ids and listeners on text views and next and back images
     */
    private void setIdAndListeners() {

        TextView tv = view.findViewById(R.id.tv_last_name);
        tv.setTextScaleX((float) 1.1);
        editText = view.findViewById(R.id.et_last_name);
        editText.addTextChangedListener(this);
        ImageView nextArrow = view.findViewById(R.id.img_next);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        nextArrow.setOnClickListener(this);
    }


    /*

   Closing soft keypad when app is not in foreground
     */
    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        super.onPause();
    }


    /*
    Opening soft keypad when app starts
     */
    private void openKeyboard() {

        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(editText, 0);
        editText.requestFocus();
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /*
    Checking if user has clicked for next then opening next fragment if last name field is not empty
    if user has clicked on back arrow then popping the current fragment
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_next) {
            if (editText.getText().toString().length() == 0)
                Toast.makeText(getContext(), "Please enter your last name", Toast.LENGTH_SHORT).show();

            else {
                Fragment f = new EmailFragment();
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

        String s1 = s.toString();
        if (s1.length() > 0) {
            char c = s1.charAt(s1.length() - 1);
            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
            } else {
                s1 = s1.substring(0, s1.length() - 1);
                editText.setText(s1);
                Toast.makeText(getActivity(), "Illegal character", Toast.LENGTH_SHORT).show();
                editText.requestFocus();
                editText.setSelection(s1.length());
            }
        }

    }
}
