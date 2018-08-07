package com.thoughtworks.training.kmj.userservice.utils;

import java.nio.charset.Charset;

public class Constants {

    public static final String USERNAME_CONFLICT = "username conflict";
    public static final String CREATE_SUCCESS = "create success";
    public static final String USERNAME_OR_PASSWORD_ERROR = "username or password error";
    public static final String LOGIN_VERIFY_SUCCESS = "login verify success";
    static final byte[] secretKey = "kmj".getBytes(Charset.defaultCharset());
    public static final String DELETE_SUCCESS = "delete success";

}
