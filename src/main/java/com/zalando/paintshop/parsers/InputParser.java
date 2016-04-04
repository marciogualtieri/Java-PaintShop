package com.zalando.paintshop.parsers;

import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.exceptions.InputIteratorException;
import com.zalando.paintshop.exceptions.InputParserException;
import com.zalando.paintshop.iterators.InputIterator;

public interface InputParser {
    TestCase[] parse(InputIterator inputIterator) throws InputParserException, InputIteratorException;
}
