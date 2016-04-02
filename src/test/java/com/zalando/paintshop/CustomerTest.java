package com.zalando.paintshop;

import com.zalando.utils.TestHelper;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CustomerTest {
    private Customer customer;

    @Before
    public void before() throws Exception {
        customer = new Customer(TestHelper.NUMBER_COLORS);
    }

    @Test
    public void whenCustomerRequestsGlossyForFirstColor_thenBatchIsSatisfactory() throws Exception {
        customer.setGlossy(0);
        assertThat(customer.isBatchesSatisfactory(TestHelper.SECOND_COLOR_ONLY_MATTE_BATCHES), equalTo(true));
    }

    @Test
    public void whenCustomerRequestsMatteForSecondColor_thenBatchIsSatisfactory() throws Exception {
        customer.setMatte(1);
        assertThat(customer.isBatchesSatisfactory(TestHelper.SECOND_COLOR_ONLY_MATTE_BATCHES), equalTo(true));
    }

    @Test
    public void whenCustomerRequestsGlossyForFirstColorAndMatteForSecondColor_thenBatchIsSatisfactory()
            throws Exception {
        customer.setGlossy(0);
        customer.setMatte(1);
        assertThat(customer.isBatchesSatisfactory(TestHelper.SECOND_COLOR_ONLY_MATTE_BATCHES), equalTo(true));
    }

    @Test
    public void whenCustomerRequestsMatteForFirstColorAndGlossyForSecondColor_thenBatchIsUnsatisfactory()
            throws Exception {
        customer.setMatte(0);
        customer.setGlossy(1);
        assertThat(customer.isBatchesSatisfactory(TestHelper.SECOND_COLOR_ONLY_MATTE_BATCHES), equalTo(false));
    }
} 
