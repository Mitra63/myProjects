package com.sadad.ib.Exception;

public class RequestFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RequestFormatException(String msg) {
        super(msg);
    }
}
