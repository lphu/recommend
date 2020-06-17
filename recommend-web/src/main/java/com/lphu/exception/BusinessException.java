package com.lphu.exception;

import com.lphu.constant.ErrorCode;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author: lphu
 * @create: 2019-01-09 20:27
 * @description:
 */

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 9215327422367136022L;
    private int code;
    private String snapshot;

    public BusinessException(ErrorCode errorCode, String snapshotFormat, Object... argArray) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.snapshot = MessageFormatter.arrayFormat(snapshotFormat, argArray).getMessage();
    }

    public BusinessException(int code, String message, String snapshotFormat, Object... argArray) {
        super(message);
        this.code = code;
        this.snapshot = MessageFormatter.arrayFormat(snapshotFormat, argArray).getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getSnapshot() {
        return snapshot;
    }
}
