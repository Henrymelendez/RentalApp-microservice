package com.rentalApp.tenantservice.exceptions;

public class UserNotAllowedException extends Exception{
    public UserNotAllowedException(String s){
        super(s);
    }
}
