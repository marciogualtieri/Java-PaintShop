package com.zalando.paintshop.exceptions;

public class InputIteratorException extends Exception {
    public InputIteratorException(String message, Exception e) {
        super(message, e);
    }
    public InputIteratorException(Exception e) {
        super(e);
    }
}