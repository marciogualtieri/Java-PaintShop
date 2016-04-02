package com.zalando.paintshop.constants;

public class ErrorMessages {
    public static final String PARSE_FILE_ERR_MSG_FORMAT = "Could not parse file [%s].";

    public static final String PARSE_NUMBER_ERR_MSG_FORMAT = "%s [%s] should be a number on line %d.";

    public static final String PARSE_CUSTOMER_PAIRS_ERR_MSG_FORMAT =
            "Customer pairs [%s] should be a number array on line %d.";

    public static final String PARSE_FINISH_CODE_RANGE_ERR_MSG_FORMAT =
            "Finish code [%s] should be 0 (glossy) or 1 (matte) on line %d: [%s].";

    public static final String PARSE_COLOR_CODE_RANGE_ERR_MSG_FORMAT =
            "Color code [%s] should be in the range 1...%d on line %d: [%s].";

    public static final String PARSE_NUMBER_PAIRS_ERR_MSG_FORMAT =
            "Number of customer pairs [%d] is invalid on line %d: [%s].";

    public static final String PARSE_UNEXPECTED_EOF_ERR_MSG = "Unexpected end of file.";

    private ErrorMessages() {
    }
}
