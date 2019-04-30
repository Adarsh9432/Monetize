package com.example.monetize.signup_and_login;

import java.io.Serializable;

public class UserDetails implements Serializable {


    ResponseData responseData;


}

class ResponseData implements Serializable {

    String firstName;
    String lastName;
    String email;
    String username;
    String phone;


}

