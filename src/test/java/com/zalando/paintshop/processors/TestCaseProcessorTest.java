package com.zalando.paintshop.processors;

import com.zalando.utils.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;

import static com.zalando.utils.TestHelper.RESULTS_FROM_SPEC;
import static com.zalando.utils.hamcrest.CustomMatchers.bitSetContains;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCaseProcessorTest {

    private TestCaseProcessor testCaseProcessor;

    @Before
    public void before() throws Exception {
        testCaseProcessor = TestCaseProcessor.create();
    }

    @Test
    public void whenProcessTestCases_thenOk() throws Exception {
        BitSet[] results = testCaseProcessor.process(TestHelper.TEST_CASES);
        assertThat(results, bitSetContains(RESULTS_FROM_SPEC));
    }

}