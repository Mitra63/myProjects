package com.sadad.ib.Exception;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
