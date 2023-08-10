package com.rentalApp.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserTokenAuthException extends AuthenticationException {
    public UserTokenAuthException(String s){
        super(s);
    }
}
