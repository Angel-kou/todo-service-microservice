package com.thoughtworks.training.zuul.utils;

import java.nio.charset.Charset;

public class Constants {

    public static final String USERNAME_CONFLICT = "username conflict";
    public static final String CREATE_SUCCESS = "create success";
    public static final String USERNAME_OR_PASSWORD_ERROR = "username or password error";
    static final byte[] secretKey = "kmj".getBytes(Charset.defaultCharset());
    public static final String DELETE_SUCCESS = "delete success";

}
