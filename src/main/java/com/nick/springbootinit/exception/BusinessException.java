package com.nick.springbootinit.exception;

import com.nick.springbootinit.common.ErrorCode;

/**
 * Exception Handler
 *
 */
public class BusinessException extends RuntimeException {

    /**
     * Error Code
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
