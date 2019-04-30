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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monetize.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserNameFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private EditText et_userName;
    private ImageView img_back;
    private ImageView next;
    private View view;
    private FrameLayout progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        this.view = view;
        setIdAndListeners();
        openKeyboard();
    }


    /*
    Setting ids and listeners on text view, edit text and back and next arrow images
     */
    private void setIdAndListeners() {
        TextView tv_userName = view.findViewById(R.id.tv_userName);
        tv_userName.setTextScaleX((float) 1.1);
        et_userName = view.findViewById(R.id.et_userName);
        et_userName.addTextChangedListener(this);
        next = view.findViewById(R.id.img_next);
        next.setOnClickListener(this);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    /*
     Opening the soft input keypad on start of current fragment
     */
    private void openKeyboard() {
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(et_userName, 0);
        et_userName.requestFocus();
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /*
    Closing the soft input keypad when app is not in foreground
     */
    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_userName.getWindowToken(), 0);
        super.onPause();
    }

    /*
      If user has clicked on next arrow then if username has atleast 6 characters then verifying user name availability by hitting api
      if user has opted for back arrow image then closing the current fragment
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_next) {

            if (et_userName.getText().toString().length() < 6)
                Toast.makeText(getContext(), "Please enter user name of atleast 6 characters", Toast.LENGTH_SHORT).show();

            else {
                next.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new ReusablesAndConstants().getRetrofit(Api.USERNAME_CHECK_URL);
                Api api = retrofit.create(Api.class);
                Call<ResponseBody> call = api.checkUserName(et_userName.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("responseData")) {
                                Fragment f = new PickPasswordFragment();
                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.fragment_container, f);
                                transaction.setCustomAnimations(R.anim.slide_down_fragment, 0);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else {
                                Toast.makeText(getContext(), "User Name already taken. Please enter another.", Toast.LENGTH_SHORT).show();
                                next.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("Monetize", "No internet connection ");
                        next.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    }
                });
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

            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c >= 48 && c <= 57)) {
            } else {
                s1 = s1.substring(0, s1.length() - 1);
                et_userName.setText(s1);
                Toast.makeText(getActivity(), "Illegal character", Toast.LENGTH_SHORT).show();
                et_userName.requestFocus();
                et_userName.setSelection(s1.length());
            }
        }

    }
}
