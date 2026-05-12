package com.User.Application.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg) {
            super(msg);
        }
}