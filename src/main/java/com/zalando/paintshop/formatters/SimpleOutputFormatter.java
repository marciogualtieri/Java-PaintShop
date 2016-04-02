package com.zalando.paintshop.formatters;

import com.zalando.paintshop.TestCase;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SimpleOutputFormatter implements OutputFormatter {
    private static final String OUTPUT_FORMAT = "Case #%d: %s";

    private SimpleOutputFormatter() {
    }

    public static OutputFormatter create() {
        return new SimpleOutputFormatter();
    }

    /**
     * Formats test case batches into output lines.
     *
     * @param testCases    test cases
     * @param batchesArray correspondent batches solution to test cases
     * @return formatted output batches.
     */
    public List<String> format(TestCase[] testCases, BitSet[] batchesArray) {
        List<String> outputs = new ArrayList<>();
        for (int i = 0; i < batchesArray.length; i++) {
            outputs.add(formatTestCaseBatches(i + 1, batchesArray[i], testCases[i]));
        }
        return outputs;
    }

    private String formatTestCaseBatches(int index, BitSet batches, TestCase testCase) {
        if (batches != null) {
            String batchesString = formatBitSet(batches, testCase.getNumColors());
            return String.format(OUTPUT_FORMAT, index, batchesString);
        } else
            return String.format(OUTPUT_FORMAT, index, "IMPOSSIBLE");
    }

    private String formatBitSet(BitSet bitSet, int numBits) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numBits; i++) {
            if (bitSet.get(i)) sb.append("1 ");
            else sb.append("0 ");
        }
        return sb.substring(0, sb.length() - 1);
    }
}