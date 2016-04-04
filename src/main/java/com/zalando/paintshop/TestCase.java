package com.zalando.paintshop;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Arrays;

/**
 * A test case contains the number of numColors, the number of customers and customer
 * preferences that need to be processed  to obtain batches of numColors that satisfy
 * all customers.
 */
public class TestCase {
    private final int numColors;
    private final Customer[] customers;

    public TestCase(int numColors, Customer[] customers) {
        this.numColors = numColors;
        this.customers = customers;
    }

    public int getNumColors() {
        return numColors;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;
        final TestCase other = (TestCase) object;
        return Objects.equal(this.numColors, other.numColors)
                && Arrays.deepEquals(this.customers, other.customers);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("numColors", numColors)
                .add("customers", Joiner.on(",").join(customers))
                .toString();
    }
}
