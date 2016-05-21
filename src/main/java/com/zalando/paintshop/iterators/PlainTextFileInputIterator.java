package com.zalando.paintshop.iterators;

import com.zalando.paintshop.exceptions.InputIteratorException;
import com.zalando.paintshop.messages.ErrorMessages;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.mutable.MutableInt;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * File line iterator capable of holding the current line number.
 */
public class PlainTextFileInputIterator implements InputIterator {
    private final LineIterator lineIterator;
    private final MutableInt lineNumber;

    private PlainTextFileInputIterator(File file) throws IOException {
        lineIterator = FileUtils.lineIterator(file);
        lineNumber = new MutableInt(0);
    }

    public static InputIterator createFromFileName(String fileNameAndPath) throws InputIteratorException {
        try {
            return new PlainTextFileInputIterator(new File(fileNameAndPath));
        } catch (IOException e) {
            throw new InputIteratorException(e);
        }
    }

    public InputIteratorLine readLine() throws InputIteratorException {
        try {
            lineNumber.increment();
            return new InputIteratorLine(lineIterator.next(), lineNumber.intValue());
        } catch (NoSuchElementException e) {
            throw new InputIteratorException(ErrorMessages.UNEXPECTED_EOF_ERR_MSG, e);
        }
    }

    public boolean hasLines() {
        return lineIterator.hasNext();
    }
}
