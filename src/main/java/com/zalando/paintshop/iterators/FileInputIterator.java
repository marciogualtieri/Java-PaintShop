package com.zalando.paintshop.iterators;

import com.zalando.paintshop.constants.ErrorMessages;
import com.zalando.paintshop.exceptions.InputIteratorException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.mutable.MutableInt;

import java.io.File;
import java.io.IOException;

/**
 * File line iterator capable of holding the current line number.
 */
public class FileInputIterator implements InputIterator {
    private final LineIterator lineIterator;
    private final MutableInt lineNumber;

    private FileInputIterator(File file) throws IOException {
        lineIterator = FileUtils.lineIterator(file);
        lineNumber = new MutableInt(0);
    }

    public static InputIterator createFromFileName(String fileNameAndPath) throws InputIteratorException {
        try {
            return new FileInputIterator(new File(fileNameAndPath));
        } catch (IOException e) {
            String message = String.format(ErrorMessages.PARSE_FILE_ERR_MSG_FORMAT, fileNameAndPath);
            throw new InputIteratorException(message, e);
        }
    }

    public String readLine() {
        lineNumber.increment();
        return lineIterator.next();
    }

    public boolean hasLines() {
        return lineIterator.hasNext();
    }

    public int getLineNumber() {
        return lineNumber.intValue();
    }
}
