package com.sadad.ib.Exception;

public class ResourceNotAccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotAccessException(String msg) {
        super(msg);
    }
}
