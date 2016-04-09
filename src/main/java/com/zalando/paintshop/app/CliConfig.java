package com.zalando.paintshop.app;

import com.beust.jcommander.Parameter;

/**
 * Command line arguments are configured here.
 */
class CliConfig {

    @Parameter(names = {"--input-file", "-i"},
            description = "Name of the input file containing test cases.",
            required = true)
    private String inputFileNameAndPath;

    @Parameter(names = {"--output-file", "-o"},
            description = "Name of the output file. If not provided will print output to console.",
            required = false)
    private String outputFileNameAndPath;

    @Parameter(names = "--help", help = true)
    private boolean help;

    public boolean getHelp() {
        return this.help;
    }

    public String getInputFileNameAndPath() {
        return this.inputFileNameAndPath;
    }

    public String getOutputFileNameAndPath() {
        return this.outputFileNameAndPath;
    }

}