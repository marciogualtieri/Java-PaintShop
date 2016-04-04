package com.zalando.paintshop.iterators;

import com.zalando.paintshop.exceptions.InputIteratorException;

public interface InputIterator {
    String readLine() throws InputIteratorException;
    int getLineNumber();
    boolean hasLines();
}
