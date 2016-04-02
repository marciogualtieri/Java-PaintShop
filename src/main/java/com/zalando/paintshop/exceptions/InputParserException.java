package com.zalando.paintshop.exceptions;

public class InputParserException extends Exception {
    public InputParserException(String message, Exception e) {
        super(message, e);
    }

    public InputParserException(String message) {
        super(message);
    }
}