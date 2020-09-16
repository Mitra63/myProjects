package com.sadad.ib.Exception;

public class InvalidBalanceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidBalanceException(String msg) {
        super(msg);
    }
}
