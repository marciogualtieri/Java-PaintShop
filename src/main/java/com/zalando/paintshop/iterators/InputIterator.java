package com.zalando.paintshop.iterators;

import com.zalando.paintshop.exceptions.InputIteratorException;

public interface InputIterator {
    InputIteratorLine readLine() throws InputIteratorException;
    boolean hasLines();
}
