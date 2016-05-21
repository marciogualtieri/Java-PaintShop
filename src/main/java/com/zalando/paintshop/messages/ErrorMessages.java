package com.zalando.paintshop.messages;

import com.zalando.paintshop.iterators.InputIteratorLine;

public class ErrorMessages {

    public static final String UNEXPECTED_EOF_ERR_MSG = "Unexpected end of file.";

    public static String notNumberErrorMessage(String name, InputIteratorLine line) {
        return String.format("%s should be a number on line %d: [%s]", name, line.getNumber(), line.getValue());
    }

    public static String invalidNumberPairsErrorMessage(InputIteratorLine line) {
        return String.format("Number of customer pairs is invalid on line %d: [%s].",
                line.getNumber(), line.getValue());
    }

    public static String nonNumericPairsErrorMessage(InputIteratorLine line) {
        return String.format("Customer pairs should be a number array on line %d: [%s].",
                line.getNumber(), line.getValue());
    }

    public static String moreThanOneMatteErrorMessage(InputIteratorLine line) {
        return String.format("More than one matte color for customer on line %d: [%s].",
                line.getNumber(), line.getValue());
    }

    public static String invalidColorCodeErrorMessage(int numColors, InputIteratorLine line) {
        return String.format("Color code should be in the range 1..%d on line %d: [%s].", numColors,
                line.getNumber(), line.getValue());
    }

    public static String invalidFinishCodeErrorMessage(InputIteratorLine line) {
        return String.format("Finish code should be 0 (glossy) or 1 (matte) on line %d: [%s].",
                line.getNumber(), line.getValue());
    }

    private ErrorMessages() {
    }
}
