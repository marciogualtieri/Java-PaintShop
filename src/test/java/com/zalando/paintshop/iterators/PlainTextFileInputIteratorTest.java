package com.zalando.paintshop.iterators;

import com.zalando.paintshop.constants.ErrorMessages;
import com.zalando.paintshop.exceptions.InputIteratorException;
import com.zalando.utils.TestHelper;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
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
        List<String> lines = readAllLinesFromIterator(inputIterator);
        List<String> expectedLines = FileUtils.readLines(new File(TestHelper.SUCCESS_FROM_SPEC_INPUT_FILE));
        assertThat(lines, contains(expectedLines.toArray()));
    }

    private List<String> readAllLinesFromIterator(InputIterator inputIterator) throws InputIteratorException {
        List<String> lines = new ArrayList<>();
        while(inputIterator.hasLines()) {
            lines.add(inputIterator.readLine());
        }
        return lines;
    }
}