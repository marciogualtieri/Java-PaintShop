package com.zalando.paintshop.constants;

public class ErrorMessages {

    public static final String UNEXPECTED_EOF_ERR_MSG = "Unexpected end of file.";

    public static String notNumberErrorMessage(String name, String lineValue, int lineNum) {
        return String.format("%s should be a number on line %d: [%s]", name, lineNum, lineValue);
    }

    public static String invalidNumberPairsErrorMessage(String lineValue, int lineNum) {
        return String.format("Number of customer pairs is invalid on line %d: [%s].", lineNum, lineValue);
    }

    public static String nonNumericPairsErrorMessage(String lineValue, int lineNum) {
        return String.format("Customer pairs should be a number array on line %d: [%s].", lineNum, lineValue);
    }

    public static String moreThanOneMatteErrorMessage(String lineValue, int lineNum) {
        return String.format("More than one matte color for customer on line %d: [%s].", lineNum, lineValue);
    }

    public static String invalidColorCodeErrorMessage(int numColors, String lineValue, int lineNum) {
        return String.format("Color code should be in the range 1..%d on line %d: [%s].", numColors, lineNum, lineValue);
    }

    public static String invalidFinishCodeErrorMessage(String lineValue, int lineNum) {
        return String.format("Finish code should be 0 (glossy) or 1 (matte) on line %d: [%s].", lineNum, lineValue);
    }

    private ErrorMessages() {
    }
}
