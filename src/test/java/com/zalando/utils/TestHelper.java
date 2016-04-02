package com.zalando.utils;

import com.zalando.paintshop.Customer;
import com.zalando.paintshop.constants.ErrorMessages;
import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.constants.FieldNames;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class TestHelper {

    public static final int NUMBER_COLORS = 10;

    public static final BitSet SECOND_COLOR_ONLY_MATTE_BATCHES = createSecondColorOnlyMatteBatches();

    public static final String SUCCESS_FROM_SPEC_INPUT_FILE =
            "src/test/resources/inputs/success_from_specification.txt";
    public static final String SUCCESS_INPUT_FILE =
            "src/test/resources/inputs/success.txt";
    public static final String NON_EXISTENT_FILE = "i/do/not.exist";
    public static final String UNEXPECTED_EOF_INPUT_FILE = "src/test/resources/inputs/unexpected_end_of_file.txt";
    public static final String NUMBER_TEST_CASES_NOT_A_NUMBER_INPUT_FILE =
            "src/test/resources/inputs/number_test_cases_not_a_number.txt";
    public static final String NUMBER_COLORS_NOT_A_NUMBER_INPUT_FILE =
            "src/test/resources/inputs/number_colors_not_a_number.txt";
    public static final String NUMBER_CUSTOMERS_NOT_A_NUMBER_INPUT_FILE =
            "src/test/resources/inputs/number_customers_not_a_number.txt";
    public static final String CUSTOMER_PAIRS_NOT_NUMBERS =
            "src/test/resources/inputs/customer_pairs_not_numbers.txt";
    public static final String INCORRECT_NUMBER_OF_PAIRS_INPUT_FILE =
            "src/test/resources/inputs/incorrect_number_of_pairs.txt";

    public static final TestCase[] TEST_CASES = createTestCases();

    public static final BitSet[] RESULTS_FROM_SPEC = createResults();

    public static final List<String> OUTPUTS_FROM_SPEC =
            Arrays.asList("Case #1: 1 0 0 0 0", "Case #2: IMPOSSIBLE");

    public static final List<String> OUTPUTS =
            Arrays.asList("Case #1: 0 0 0 0 0 0 0 0", "Case #2: 0 0 0 0 0 0 1 0", "Case #3: IMPOSSIBLE");

    public static final String INPUT_FILE_DOES_NOT_EXIST_MESSAGE =
            String.format(ErrorMessages.PARSE_FILE_ERR_MSG_FORMAT, NON_EXISTENT_FILE);

    public static final String NUMBER_TEST_CASES_NOT_A_NUMBER_MESSAGE =
            String.format(ErrorMessages.PARSE_NUMBER_ERR_MSG_FORMAT, FieldNames.NUMBER_TEST_CASES, "X", 1);

    public static final String NUMBER_COLORS_NOT_A_NUMBER_MESSAGE =
            String.format(ErrorMessages.PARSE_NUMBER_ERR_MSG_FORMAT, FieldNames.NUMBER_COLORS, "X", 2);

    public static final String NUMBER_CUSTOMERS_NOT_A_NUMBER_MESSAGE =
            String.format(ErrorMessages.PARSE_NUMBER_ERR_MSG_FORMAT, FieldNames.NUMBER_CUSTOMERS, "X", 3);

    public static final String CUSTOMER_PAIRS_NOT_NUMBERS_MESSAGE =
            String.format(ErrorMessages.PARSE_CUSTOMER_PAIRS_ERR_MSG_FORMAT, "2 1 0 X 0", 5);

    public static final String INCORRECT_NUMBER_OF_PAIRS_MESSAGE =
            String.format(ErrorMessages.PARSE_NUMBER_PAIRS_ERR_MSG_FORMAT, 3, 5, "3 1 0 2 0");

    private static BitSet createSecondColorOnlyMatteBatches() {
        BitSet set = new BitSet(NUMBER_COLORS);
        set.set(1);
        return set;
    }

    private static TestCase[] createTestCases() {
        TestCase[] testCases = new TestCase[2];
        testCases[0] = createFirstTestCase();
        testCases[1] = createSecondTestCase();
        return testCases;
    }

    private static BitSet[] createResults() {
        BitSet[] results = new BitSet[2];
        BitSet firstColorOnlyMatte = new BitSet(NUMBER_COLORS);
        firstColorOnlyMatte.set(0);
        results[0] = firstColorOnlyMatte;
        results[1] = null;
        return results;
    }

    /**
     * First test case in resources/inputs/success_from_specification.txt
     */
    private static TestCase createFirstTestCase() {
        Customer[] customers = new Customer[3];
        customers[0] = new Customer(5);
        customers[0].setMatte(0);
        customers[1] = new Customer(5);
        customers[1].setGlossy(0);
        customers[1].setGlossy(1);
        customers[2] = new Customer(5);
        customers[2].setGlossy(4);
        return new TestCase(5, customers);
    }

    /**
     * Second test case in resources/inputs/success_from_specification.txt
     */
    private static TestCase createSecondTestCase() {
        Customer[] customers = new Customer[2];
        customers[0] = new Customer(1);
        customers[0].setGlossy(0);
        customers[1] = new Customer(1);
        customers[1].setMatte(0);
        return new TestCase(1, customers);
    }

    private TestHelper() {
    }
}
