package com.zalando.paintshop.app;

import com.beust.jcommander.JCommander;
import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.exceptions.InputIteratorException;
import com.zalando.paintshop.exceptions.InputParserException;
import com.zalando.paintshop.formatters.OutputFormatter;
import com.zalando.paintshop.formatters.SimpleOutputFormatter;
import com.zalando.paintshop.iterators.FileInputIterator;
import com.zalando.paintshop.iterators.InputIterator;
import com.zalando.paintshop.parsers.InputParser;
import com.zalando.paintshop.parsers.PlainTextInputParser;
import com.zalando.paintshop.processors.TestCaseProcessor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import java.util.List;

/**
 * Main command-line application.
 */
public class PaintShop {
    private static final String BENCHMARK_OUTPUT_FORMAT = "\nTotal processing time: %d ms\n";

    private final InputParser inputParser;
    private final TestCaseProcessor testCaseProcessor;
    private final OutputFormatter outputFormatter;

    public PaintShop() {
        inputParser = PlainTextInputParser.create();
        testCaseProcessor = TestCaseProcessor.create();
        outputFormatter = SimpleOutputFormatter.create();
    }

    public List<String> execute(String fileNameAndPath) throws
            InputParserException, InputIteratorException {
        InputIterator inputIterator = FileInputIterator.createFromFileName(fileNameAndPath);
        TestCase[] testCases = inputParser.parse(inputIterator);
        long startTime = System.currentTimeMillis();
        BitSet[] batchesArray = testCaseProcessor.process(testCases);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format(BENCHMARK_OUTPUT_FORMAT, (endTime - startTime)));
        return outputFormatter.format(testCases, batchesArray);
    }

    public static void main(String[] args) {
        try {
            PaintShopCliArguments paintShopCliArguments = parseCliArguments(args);
            String inputFileNameAndPath = paintShopCliArguments.getInputFileNameAndPath();
            PaintShop paintShop = new PaintShop();
            List<String> outputs = paintShop.execute(inputFileNameAndPath);
            if (isOutputFileProvided(paintShopCliArguments))
                outputToFile(outputs, paintShopCliArguments.getOutputFileNameAndPath());
            else
                outputToConsole(outputs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static PaintShopCliArguments parseCliArguments(String[] args) throws Exception {
        PaintShopCliArguments paintShopCliArguments = new PaintShopCliArguments();
        JCommander jCommander = new JCommander(paintShopCliArguments, args);
        if (paintShopCliArguments.getHelp()) {
            jCommander.usage();
            throw new Exception("");
        }
        return paintShopCliArguments;
    }

    private static boolean isOutputFileProvided(PaintShopCliArguments paintShopCliArguments) {
        return paintShopCliArguments.getOutputFileNameAndPath() != null;
    }

    private static void outputToConsole(List<String> outputs) {
        outputs.forEach(System.out::println);
    }

    private static void outputToFile(List<String> outputs, String fileNameAndPath) throws IOException {
        FileUtils.writeLines(new File(fileNameAndPath), outputs);
    }
}
