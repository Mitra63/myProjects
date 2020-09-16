package com.sadad.ib.Exception;

public class AccountIsBlockedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccountIsBlockedException(String msg) {
        super(msg);
    }
}
