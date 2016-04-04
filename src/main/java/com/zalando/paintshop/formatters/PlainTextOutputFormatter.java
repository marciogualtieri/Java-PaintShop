package com.zalando.paintshop.formatters;

import com.zalando.paintshop.TestCase;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class PlainTextOutputFormatter implements OutputFormatter {
    private static final String OUTPUT_FORMAT = "Case #%d: %s";

    private PlainTextOutputFormatter() {
    }

    public static OutputFormatter create() {
        return new PlainTextOutputFormatter();
    }

    /**
     * Formats test case solutions into output lines.
     *
     * @param testCases    test cases
     * @param solutions correspondent solutions to test cases
     * @return formatted output batches.
     */
    public List<String> format(TestCase[] testCases, BitSet[] solutions) {
        List<String> outputs = new ArrayList<>();
        for (int i = 0; i < solutions.length; i++) {
            outputs.add(formatTestCaseSolutions(i + 1, solutions[i], testCases[i]));
        }
        return outputs;
    }

    private String formatTestCaseSolutions(int index, BitSet solution, TestCase testCase) {
        if (solution != null) {
            String batchesString = formatBitSet(solution, testCase.getNumColors());
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