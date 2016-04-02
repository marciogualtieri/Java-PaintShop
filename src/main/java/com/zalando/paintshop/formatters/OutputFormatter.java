package com.zalando.paintshop.formatters;

import com.zalando.paintshop.TestCase;

import java.util.BitSet;
import java.util.List;

public interface OutputFormatter {
    List<String> format(TestCase[] testCases, BitSet[] batchesArray);
}
