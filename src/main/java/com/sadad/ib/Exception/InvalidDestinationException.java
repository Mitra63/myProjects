package com.sadad.ib.Exception;

public class InvalidDestinationException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidDestinationException(String msg) {
        super(msg);
    }
}
