package com.zalando.paintshop.processors;

import com.zalando.paintshop.Customer;
import com.zalando.paintshop.TestCase;

import java.util.BitSet;

public class TestCaseProcessor {

    private TestCaseProcessor() {
    }

    public static TestCaseProcessor create() {
        return new TestCaseProcessor();
    }

    /**
     * Process test cases and generate correspondent batches.
     * @param testCases An array of test cases to process.
     * @return batches that satisfies all customers, null if impossible.
     */
    public BitSet[] process(TestCase[] testCases) {
        BitSet[] batchesArray = new BitSet[testCases.length];
        for(int i = 0; i < testCases.length; i++) {
            batchesArray[i] = processTestCase(testCases[i]);
        }
        return batchesArray;
    }

    private BitSet processTestCase(TestCase testCase) {
        BitSet batches = new BitSet(testCase.getNumColors());
        Customer[] customers = testCase.getCustomers();
        for(Customer customer : customers) {
            if(!customer.isBatchesSatisfactory(batches) && customer.hasMatte())
                batches.set(customer.getMatte());
        }
        if(isBatchSatisfactoryToAllCustomers(batches, customers))
            return batches;
        return null;
    }

    private boolean isBatchSatisfactoryToAllCustomers(BitSet batches, Customer[] customers) {
        for(Customer customer : customers) {
            if(!customer.isBatchesSatisfactory(batches)) return false;
        }
        return true;
    }
}
