package com.zalando.paintshop.parsers;

import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.exceptions.InputParserException;
import com.zalando.paintshop.iterators.InputIterator;
import com.zalando.paintshop.iterators.PlainTextFileInputIterator;
import com.zalando.utils.TestHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlainTextInputParserTest {

    private InputParser plainTextInputParser;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() throws Exception {
        plainTextInputParser = PlainTextInputParser.create();
    }

    @Test
    public void whenParseFile_thenOK() throws Exception {
        InputIterator inputIterator = PlainTextFileInputIterator.createFromFileName(TestHelper.SUCCESS_FROM_SPEC_INPUT_FILE);
        TestCase[] testCases = plainTextInputParser.parse(inputIterator);
        assertThat(testCases, equalTo(TestHelper.TEST_CASES));
    }

    @Test
    public void whenParseFileAndNumberTestCasesNotANumber_thenException() throws Exception {
        setUpInputParserException(TestHelper.NUMBER_TEST_CASES_NOT_A_NUMBER_MESSAGE);
        InputIterator inputIterator =
                PlainTextFileInputIterator.createFromFileName(TestHelper.NUMBER_TEST_CASES_NOT_A_NUMBER_INPUT_FILE);
        plainTextInputParser.parse(inputIterator);
    }

    @Test
    public void whenParseFileAndNumberColorsNotANumber_thenException() throws Exception {
        setUpInputParserException(TestHelper.NUMBER_COLORS_NOT_A_NUMBER_MESSAGE);
        InputIterator inputIterator =
                PlainTextFileInputIterator.createFromFileName(TestHelper.NUMBER_COLORS_NOT_A_NUMBER_INPUT_FILE);
        plainTextInputParser.parse(inputIterator);
    }

    @Test
    public void whenParseFileAndNumberCustomersNotANumber_thenException() throws Exception {
        setUpInputParserException(TestHelper.NUMBER_CUSTOMERS_NOT_A_NUMBER_MESSAGE);
        InputIterator inputIterator =
                PlainTextFileInputIterator.createFromFileName(TestHelper.NUMBER_CUSTOMERS_NOT_A_NUMBER_INPUT_FILE);
        plainTextInputParser.parse(inputIterator);
    }

    @Test
    public void whenParseFileAndNotANumberInCustomer_thenException() throws Exception {
        setUpInputParserException(TestHelper.CUSTOMER_PAIRS_NOT_NUMBERS_MESSAGE);
        InputIterator inputIterator =
                PlainTextFileInputIterator.createFromFileName(TestHelper.CUSTOMER_PAIRS_NOT_NUMBERS);
        plainTextInputParser.parse(inputIterator);
    }

    @Test
    public void whenParseFileAndInvalidNumberOfPairs_thenException() throws Exception {
        setUpInputParserException(TestHelper.INCORRECT_NUMBER_OF_PAIRS_MESSAGE);
        InputIterator inputIterator =
                PlainTextFileInputIterator.createFromFileName(TestHelper.INCORRECT_NUMBER_OF_PAIRS_INPUT_FILE);
        plainTextInputParser.parse(inputIterator);
    }

    @Test
    public void whenParseFileAndCustomerWithMoreThanOneMatteColor_thenException() throws Exception {
        setUpInputParserException(TestHelper.CUSTOMER_WITH_MORE_THAN_ONE_MATTE_COLOR_MESSAGE);
        InputIterator inputIterator = PlainTextFileInputIterator.createFromFileName(
                        TestHelper.MORE_THAN_ONE_MATTE_COLOR_INPUT_FILE);
        plainTextInputParser.parse(inputIterator);
    }

    @Test
    public void whenParseFileAndCustomerWithInvalidColor_thenException() throws Exception {
        setUpInputParserException(TestHelper.INVALID_COLOR_INPUT_MESSAGE);
        InputIterator inputIterator = PlainTextFileInputIterator.createFromFileName(
                TestHelper.INVALID_COLOR_INPUT_FILE);
        plainTextInputParser.parse(inputIterator);
    }

    @Test
    public void whenParseFileAndCustomerWithInvalidFinish_thenException() throws Exception {
        setUpInputParserException(TestHelper.INVALID_FINISH_INPUT_MESSAGE);
        InputIterator inputIterator = PlainTextFileInputIterator.createFromFileName(
                TestHelper.INVALID_FINISH_INPUT_FILE);
        plainTextInputParser.parse(inputIterator);
    }

    private void setUpInputParserException(String message) {
        expectedException.expect(InputParserException.class);
        expectedException.expectMessage(message);
    }

}