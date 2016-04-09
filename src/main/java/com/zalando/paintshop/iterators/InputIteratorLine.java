package com.zalando.paintshop.iterators;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class InputIteratorLine {
    private final String value;
    private final int number;

    public InputIteratorLine(String value, int number) {
        this.value = value;
        this.number = number;
    }

    public String getValue() {
        return value;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;
        final InputIteratorLine other = (InputIteratorLine) object;
        return Objects.equal(this.value, other.value)
                && Objects.equal(this.number, other.number);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("number", number)
                .toString();

    }
}
