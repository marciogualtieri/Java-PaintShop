package com.zalando.paintshop.app;

import com.beust.jcommander.JCommander;
import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.exceptions.InputIteratorException;
import com.zalando.paintshop.exceptions.InputParserException;
import com.zalando.paintshop.formatters.OutputFormatter;
import com.zalando.paintshop.formatters.SimpleOutputFormatter;
import com.zalando.paintshop.iterators.InputIterator;
import com.zalando.paintshop.iterators.PlainTextFileInputIterator;
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

    private PaintShop(InputParser inputParser, TestCaseProcessor testCaseProcessor,
                      OutputFormatter outputFormatter) {
        this.inputParser = inputParser;
        this.testCaseProcessor = testCaseProcessor;
        this.outputFormatter = outputFormatter;
    }

    public static PaintShop create() {
        return new PaintShop(PlainTextInputParser.create(),
                TestCaseProcessor.create(),
                SimpleOutputFormatter.create());
    }

    public List<String> execute(String fileNameAndPath) throws
            InputParserException, InputIteratorException, IOException {
        InputIterator inputIterator = PlainTextFileInputIterator.createFromFileName(fileNameAndPath);
        TestCase[] testCases = inputParser.parse(inputIterator);
        long startTime = System.currentTimeMillis();
        BitSet[] batchesArray = testCaseProcessor.process(testCases);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format(BENCHMARK_OUTPUT_FORMAT, (endTime - startTime)));
        return outputFormatter.format(testCases, batchesArray);
    }

    public static void main(String[] args) {
        try {
            CliArgsConfig cliArgsConfig = buildCliArgsConfig(args);
            String inputFileNameAndPath = cliArgsConfig.getInputFileNameAndPath();
            PaintShop paintShop = PaintShop.create();
            List<String> outputs = paintShop.execute(inputFileNameAndPath);
            if (isOutputFileProvided(cliArgsConfig))
                outputToFile(outputs, cliArgsConfig.getOutputFileNameAndPath());
            else
                outputToConsole(outputs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static CliArgsConfig buildCliArgsConfig(String[] args) throws Exception {
        CliArgsConfig cliArgsConfig = new CliArgsConfig();
        JCommander jCommander = new JCommander(cliArgsConfig, args);
        if (cliArgsConfig.getHelp()) {
            jCommander.usage();
            throw new Exception("Help Invoked.");
        }
        return cliArgsConfig;
    }

    private static boolean isOutputFileProvided(CliArgsConfig cliArgsConfig) {
        return cliArgsConfig.getOutputFileNameAndPath() != null;
    }

    private static void outputToConsole(List<String> outputs) {
        outputs.forEach(System.out::println);
    }

    private static void outputToFile(List<String> outputs, String fileNameAndPath) throws IOException {
        FileUtils.writeLines(new File(fileNameAndPath), outputs);
    }
}
