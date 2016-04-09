package com.zalando.paintshop.app;

import com.beust.jcommander.JCommander;
import com.zalando.paintshop.TestCase;
import com.zalando.paintshop.exceptions.InputIteratorException;
import com.zalando.paintshop.exceptions.InputParserException;
import com.zalando.paintshop.formatters.OutputFormatter;
import com.zalando.paintshop.formatters.PlainTextOutputFormatter;
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
                PlainTextOutputFormatter.create());
    }

    public List<String> execute(String fileNameAndPath) throws
            InputParserException, InputIteratorException, IOException {
        InputIterator inputIterator = PlainTextFileInputIterator.createFromFileName(fileNameAndPath);
        TestCase[] testCases = inputParser.parse(inputIterator);
        BitSet[] solutions = processTestCasesWithBenchmarking(testCases);
        return outputFormatter.format(testCases, solutions);
    }

    public BitSet[] processTestCasesWithBenchmarking(TestCase[] testCases) {
        long startTime = System.currentTimeMillis();
        BitSet[] solutions = testCaseProcessor.process(testCases);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format(BENCHMARK_OUTPUT_FORMAT, (endTime - startTime)));
        return solutions;
    }

    public static void main(String[] args) {
        try {
            CliConfig cliConfig = buildCliArgsConfig(args);
            String inputFileNameAndPath = cliConfig.getInputFileNameAndPath();
            PaintShop paintShop = PaintShop.create();
            List<String> outputs = paintShop.execute(inputFileNameAndPath);
            if (isOutputFileProvided(cliConfig))
                outputToFile(outputs, cliConfig.getOutputFileNameAndPath());
            else
                outputToConsole(outputs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static CliConfig buildCliArgsConfig(String[] args) throws Exception {
        CliConfig cliConfig = new CliConfig();
        JCommander jCommander = new JCommander(cliConfig, args);
        jCommander.setProgramName("java -jar <jar name>");
        if (cliConfig.getHelp()) {
            jCommander.usage();
            throw new Exception("Help Invoked.");
        }
        return cliConfig;
    }

    private static boolean isOutputFileProvided(CliConfig cliConfig) {
        return cliConfig.getOutputFileNameAndPath() != null;
    }

    private static void outputToConsole(List<String> outputs) {
        outputs.forEach(System.out::println);
    }

    private static void outputToFile(List<String> outputs, String fileNameAndPath) throws IOException {
        FileUtils.writeLines(new File(fileNameAndPath), outputs);
    }
}
