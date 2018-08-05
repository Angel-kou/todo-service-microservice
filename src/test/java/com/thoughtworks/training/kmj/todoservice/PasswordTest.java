package com.thoughtworks.training.kmj.todoservice;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertTrue;


public class PasswordTest {
    /*
    * blowfish
    * */
    @Test
    public void shouldEncryptPassword() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("password");
        assertTrue(encoder.matches("password",encodedPassword));
    }
}
