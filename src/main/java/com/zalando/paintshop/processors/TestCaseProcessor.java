package com.zalando.paintshop.processors;

import com.zalando.paintshop.Customer;
import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.exceptions.TestCaseProcessorException;

import java.util.BitSet;

public class TestCaseProcessor {

    private TestCaseProcessor() {
    }

    public static TestCaseProcessor create() {
        return new TestCaseProcessor();
    }

    /**
     * Process test cases and generate correspondent batches.
     *
     * @param testCases An array of test cases to process.
     * @return batches that satisfies all customers, null if impossible.
     */
    public BitSet[] process(TestCase[] testCases) {
        BitSet[] batchesArray = new BitSet[testCases.length];
        for (int i = 0; i < testCases.length; i++) {
            batchesArray[i] = processTestCase(testCases[i]);
        }
        return batchesArray;
    }

    private BitSet processTestCase(TestCase testCase) {
        BitSet batches = new BitSet(testCase.getNumColors());
        Customer[] customers = testCase.getCustomers();
        try {
            while (!isBatchesFixed(batches, customers)) {
            }
        } catch (TestCaseProcessorException e) {
            return null;
        }
        return batches;
    }

    private Boolean isBatchesFixed(BitSet batches, Customer[] customers)
            throws TestCaseProcessorException {
        boolean fixed = true;
        for (Customer customer : customers) {
            if (!customer.isBatchesSatisfactory(batches)) {
                attemptToFixBatchesBySettingMatteColor(batches, customer);
                fixed = false;
            }
        }
        return fixed;
    }

    private void attemptToFixBatchesBySettingMatteColor(BitSet batches, Customer customer)
            throws TestCaseProcessorException {
        if (customer.hasMatte())
            batches.set(customer.getMatte());
        else
            throw new TestCaseProcessorException("Batches cannot be fixed.");
    }
}
