package com.zalando.paintshop.iterators;

import com.zalando.paintshop.messages.ErrorMessages;
import com.zalando.paintshop.exceptions.InputIteratorException;
import com.zalando.utils.TestHelper;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class PlainTextFileInputIteratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void whenCreateIteratorForFileAndFileDoesNotExist_thenException() throws Exception {
        expectedException.expect(InputIteratorException.class);
        expectedException.expectMessage(TestHelper.INPUT_FILE_DOES_NOT_EXIST_MESSAGE);
        PlainTextFileInputIterator.createFromFileName(TestHelper.NON_EXISTENT_FILE);
    }

    @Test
    public void whenParseFileAndLessTestCasesThanExpected_thenException() throws Exception {
        expectedException.expect(InputIteratorException.class);
        expectedException.expectMessage(ErrorMessages.UNEXPECTED_EOF_ERR_MSG);
        InputIterator inputIterator = PlainTextFileInputIterator.createFromFileName(TestHelper.UNEXPECTED_EOF_INPUT_FILE);
        inputIterator.readLine();
    }

    @Test
    public void whenCreateFileIterator_thenOK() throws Exception {
        InputIterator inputIterator =
                PlainTextFileInputIterator.createFromFileName(TestHelper.SUCCESS_FROM_SPEC_INPUT_FILE);
        List<InputIteratorLine> lines = readAllLinesFromIterator(inputIterator);
        List<InputIteratorLine> expectedLines = readAllLinesFromFile(TestHelper.SUCCESS_FROM_SPEC_INPUT_FILE);
        assertThat(lines, contains(expectedLines.toArray()));
    }

    private List<InputIteratorLine> readAllLinesFromIterator(InputIterator inputIterator) throws InputIteratorException {
        List<InputIteratorLine> lines = new ArrayList<>();
        while(inputIterator.hasLines()) {
            lines.add(inputIterator.readLine());
        }
        return lines;
    }

    private List<InputIteratorLine> readAllLinesFromFile(String fileNameAndPath) throws IOException {
        List<InputIteratorLine> lines = new ArrayList<>();
        List<String> lineValues =
                FileUtils.readLines(new File(fileNameAndPath));
        int lineNumber = 1;
        for(String value : lineValues) {
            lines.add(new InputIteratorLine(value, lineNumber++));
        }
        return lines;
    }

}