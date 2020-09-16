package com.sadad.ib.Exception;

public class AccountNumberNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccountNumberNotFoundException(String msg) {
        super(msg);
    }
}
