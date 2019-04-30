package com.example.monetize.signup_and_login;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monetize.R;


public class FirstNameFragment extends Fragment implements View.OnClickListener, TextWatcher {


    private EditText editText;
    private ImageView img_back;
    private View view;
    private TextView tv_first_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_name, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.view = view;
        setIdAndListeners();

    }

    /*
    Setting ids and listeners for edit texts and next and back images
     */
    private void setIdAndListeners() {
        TextView tv = view.findViewById(R.id.tv_first_name);
        editText = view.findViewById(R.id.et_first_name);
        editText.addTextChangedListener(this);
        ImageView nextArrow = view.findViewById(R.id.img_next);
        nextArrow.setOnClickListener(this);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        viewCalculations(tv, editText, nextArrow);
        tv_first_name = view.findViewById(R.id.tv_first_name);

    }


    private void viewCalculations(TextView tv, EditText et, ImageView img) {
        tv.setTextScaleX((float) 1.1);
        et.requestFocus();


    }

    /*
     Checking if user has clicked on next then if user name has filled his first name moving on next fragment
     if user has clicked for going back then popping the current fragment from back stack
     */
    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.img_next) {
            if (editText.getText().toString().length() == 0)
                Toast.makeText(getContext(), "Please enter your first name", Toast.LENGTH_SHORT).show();

            else {

                Fragment f = new LastNameFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, f);
                transaction.setCustomAnimations(R.anim.slide_down_fragment, 0);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        } else if (v.getId() == R.id.img_back) {
            getActivity().finish();
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
