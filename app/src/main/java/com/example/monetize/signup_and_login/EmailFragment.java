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


public class EmailFragment extends Fragment implements View.OnClickListener {


    private ImageView next;
    private FrameLayout progressBar;
    private EditText et_email;
    private ImageView img_back;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        this.view = view;
        setIdAndListeners();
        openKeyboard();
    }


    /*
       Setting ids and listeners on text views and next and back images
     */
    private void setIdAndListeners() {

        TextView email = view.findViewById(R.id.tv_email);
        email.setTextScaleX((float) 1.1);
        et_email = view.findViewById(R.id.et_email);
        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        progressBar = view.findViewById(R.id.progress_bar);
    }


    /*
       opening soft keypad on start of application
     */
    private void openKeyboard() {
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        et_email.requestFocus();
        imgr.showSoftInput(et_email, 0);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /*
     Closing keypad when application is not in foreground
     */
    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_email.getWindowToken(), 0);
        super.onPause();
    }


    /*
      if user has clicked for next then validating email text and if email is proper then hitting the api for checking email availability
      if user has opted for going back then popping the current fragment
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.next) {

            if (et_email.getText().toString().length() == 0)
                Toast.makeText(getContext(), "Please enter the email", Toast.LENGTH_SHORT).show();

            else if (emailValidation(et_email.getText().toString())) {
                next.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new ReusablesAndConstants().getRetrofit(Api.EMAIL_CHECK_URL);

                Api api = retrofit.create(Api.class);
                Call<ResponseBody> call = api.checkEmail(et_email.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("responseData")) {
                                Fragment f = new NumberFragment();
                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.fragment_container, f);
                                transaction.setCustomAnimations(R.anim.slide_down_fragment, 0);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else if (!jsonObject.getBoolean("responseData")) {
                                Toast.makeText(getContext(), "Email alredy exist", Toast.LENGTH_SHORT).show();
                                next.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        next.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } else
                Toast.makeText(getContext(), "Please enter proper email", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.img_back) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
        }

    }

    /*
      Validating email on regex pattern
     */
    private boolean emailValidation(String s) {
        String email_regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        return s.matches(email_regex);

    }

}
