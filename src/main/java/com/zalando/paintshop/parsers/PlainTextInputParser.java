package com.zalando.paintshop.parsers;

import com.zalando.paintshop.Customer;
import com.zalando.paintshop.constants.ErrorMessages;
import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.constants.FieldNames;
import com.zalando.paintshop.enums.Finish;
import com.zalando.paintshop.exceptions.InputParserException;
import com.zalando.paintshop.iterators.InputIterator;

import java.util.Arrays;
import java.util.NoSuchElementException;

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
    public TestCase[] parse(InputIterator lines) throws InputParserException {
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
            throws InputParserException {
        Customer[] customers = new Customer[numCustomers];
        for (int i = 0; i < numCustomers; i++) {
            Customer customer = parseCustomer(numColors, lines);
            customers[i] = customer;
        }
        return customers;
    }

    private Customer parseCustomer(int numColors, InputIterator lines)
            throws InputParserException {
        Customer customer = new Customer(numColors);
        int[] pairs = parseCustomerPairs(lines);
        int lineNum = lines.getLineNumber();
        for (int i = 1; i < pairs.length; i += 2) {
            int color = pairs[i];
            int finish = pairs[i + 1];
            validateColorCode(color, numColors, lineNum);
            validateFinishCode(finish, lineNum);
            updateCustomer(customer, color, finish);
        }
        return customer;
    }

    private void updateCustomer(Customer customer, int colorCode, int finishCode)
            throws InputParserException {
        if (Finish.isGlossy(finishCode)) customer.setGlossy(colorCode - 1);
        else customer.setMatte(colorCode - 1);
    }

    private void validateColorCode(int colorCode, int numColors, int lineNum) throws InputParserException {
        if (colorCode < 1 || colorCode > numColors)
            throw new InputParserException(String.format(ErrorMessages.PARSE_COLOR_CODE_RANGE_ERR_MSG_FORMAT,
                    colorCode, numColors, lineNum));
    }

    private void validateFinishCode(int finishCode, int lineNum) throws InputParserException {
        if (!Finish.isValidFinishCode(finishCode))
            throw new InputParserException(String.format(ErrorMessages.PARSE_FINISH_CODE_RANGE_ERR_MSG_FORMAT,
                    finishCode, lineNum));
    }

    private int[] parseCustomerPairs(InputIterator lines)
            throws InputParserException {
        String line = null;
        int[] pairs;
        int lineNum = 0;
        try {
            line = lines.readLine();
            lineNum = lines.getLineNumber();
            pairs = convertStringToIntArray(line, "\\s");
        } catch (NumberFormatException e) {
            throw new InputParserException(
                    String.format(ErrorMessages.PARSE_CUSTOMER_PAIRS_ERR_MSG_FORMAT, line, lineNum), e);
        } catch (NoSuchElementException e) {
            throw new InputParserException(String.format(ErrorMessages.PARSE_UNEXPECTED_EOF_ERR_MSG), e);
        }
        validateCustomerPairs(pairs, line, lineNum);
        return pairs;
    }

    private void validateCustomerPairs(int[] pairs, String line, int lineNum) throws InputParserException {
        if (pairs == null || !isValidNumCustomerPairs(pairs))
            throw new InputParserException(String.format(
                    ErrorMessages.PARSE_NUMBER_PAIRS_ERR_MSG_FORMAT, pairs[0], lineNum, line));
    }

    private boolean isValidNumCustomerPairs(int[] pairs) {
        int numPairs = pairs[0];
        return (pairs.length - 1) / 2 == numPairs && (pairs.length - 1) % 2 == 0;
    }

    private int[] convertStringToIntArray(String string, String delimiter) {
        return Arrays.stream(string.split(delimiter))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private int parseIntField(InputIterator lines, String fieldName) throws InputParserException {
        int lineNum = 0;
        String value = null;
        try {
            value = lines.readLine();
            lineNum = lines.getLineNumber();
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InputParserException(
                    String.format(ErrorMessages.PARSE_NUMBER_ERR_MSG_FORMAT, fieldName, value, lineNum), e);
        } catch (NoSuchElementException e) {
            throw new InputParserException(ErrorMessages.PARSE_UNEXPECTED_EOF_ERR_MSG);
        }
    }
}
