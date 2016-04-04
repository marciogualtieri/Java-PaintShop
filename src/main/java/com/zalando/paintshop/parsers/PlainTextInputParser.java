package com.zalando.paintshop.parsers;

import com.zalando.paintshop.Customer;
import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.constants.FieldNames;
import com.zalando.paintshop.enums.Finish;
import com.zalando.paintshop.exceptions.InputIteratorException;
import com.zalando.paintshop.exceptions.InputParserException;
import com.zalando.paintshop.iterators.InputIterator;

import java.util.Arrays;

import static com.zalando.paintshop.constants.ErrorMessages.*;

/**
 * Parses plain text input into test cases.
 */
public class PlainTextInputParser implements InputParser {

    private PlainTextInputParser() {
    }

    public static InputParser create() {
        return new PlainTextInputParser();
    }

    /**
     * Parses plain text input into an array of test cases.
     *
     * @param lines Iterator for text lines to parse into test cases.
     * @return An array of test cases.
     * @throws InputParserException In case file doesn't comply to syntax.
     */
    public TestCase[] parse(InputIterator lines) throws InputParserException, InputIteratorException {
        TestCase[] testCases;
        int numTestCases = parseIntField(lines, FieldNames.NUMBER_TEST_CASES);
        testCases = new TestCase[numTestCases];
        for (int i = 0; i < numTestCases; i++) {
            int numColors = parseIntField(lines, FieldNames.NUMBER_COLORS);
            int numCustomers = parseIntField(lines, FieldNames.NUMBER_CUSTOMERS);
            Customer[] customers = parseCustomers(numCustomers, numColors, lines);
            testCases[i] = new TestCase(numColors, customers);
        }
        return testCases;
    }

    private Customer[] parseCustomers(int numCustomers, int numColors, InputIterator lines)
            throws InputParserException, InputIteratorException {
        Customer[] customers = new Customer[numCustomers];
        for (int i = 0; i < numCustomers; i++) {
            Customer customer = parseCustomer(numColors, lines);
            customers[i] = customer;
        }
        return customers;
    }

    private Customer parseCustomer(int numColors, InputIterator lines)
            throws InputParserException, InputIteratorException {
        Customer customer = new Customer(numColors);
        String lineValue = lines.readLine();
        int lineNum = lines.getLineNumber();
        int[] pairs = parseCustomerPairs(lineValue, lineNum);
        for (int i = 1; i < pairs.length; i += 2) {
            int color = pairs[i];
            int finish = pairs[i + 1];
            validateColorCode(color, numColors, lineValue, lineNum);
            validateFinishCode(customer, finish, lineValue, lineNum);
            updateCustomer(customer, color, finish);
        }
        return customer;
    }

    private void updateCustomer(Customer customer, int colorCode, int finishCode)
            throws InputParserException {
        if (Finish.isGlossy(finishCode)) customer.setGlossy(colorCode - 1);
        else customer.setMatte(colorCode - 1);
    }

    private void validateColorCode(int colorCode, int numColors, String lineValue, int lineNum)
            throws InputParserException {
        if (colorCode < 1 || colorCode > numColors)
            throw new InputParserException(invalidColorCodeErrorMessage(numColors, lineValue, lineNum));
    }

    private void validateFinishCode(Customer customer, int finishCode, String lineValue, int lineNum)
            throws InputParserException {
        if (Finish.isMatte(finishCode) && customer.hasMatte()) {
            throw new InputParserException(moreThanOneMatteErrorMessage(lineValue, lineNum));
        }
        if (!Finish.isValidFinishCode(finishCode))
            throw new InputParserException(invalidFinishCodeErrorMessage(lineValue, lineNum));
    }

    private int[] parseCustomerPairs(String lineValue, int lineNum)
            throws InputParserException, InputIteratorException {
        int[] pairs;
        try {
            pairs = convertCustomerStringToIntArray(lineValue);
        } catch (NumberFormatException e) {
            throw new InputParserException(nonNumericPairsErrorMessage(lineValue, lineNum), e);
        }
        validateCustomerPairs(pairs, lineValue, lineNum);
        return pairs;
    }

    private void validateCustomerPairs(int[] pairs, String lineValue, int lineNum) throws InputParserException {
        if (pairs == null || !isValidNumCustomerPairs(pairs))
            throw new InputParserException(invalidNumberPairsErrorMessage(lineValue, lineNum));
    }

    private boolean isValidNumCustomerPairs(int[] pairs) {
        int numPairs = pairs[0];
        return (pairs.length - 1) / 2 == numPairs && (pairs.length - 1) % 2 == 0;
    }

    private int[] convertCustomerStringToIntArray(String string) {
        return Arrays.stream(string.split("\\s"))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private int parseIntField(InputIterator lines, String fieldName)
            throws InputParserException, InputIteratorException {
        String lineValue = lines.readLine();
        int lineNum = lines.getLineNumber();
        try {
            return Integer.parseInt(lineValue);
        } catch (NumberFormatException e) {
            throw new InputParserException(notNumberErrorMessage(fieldName, lineValue, lineNum), e);
        }
    }
}
