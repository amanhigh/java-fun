package com.fun.service;

import lombok.Getter;

public class FunException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final Object details;

    public FunException(ErrorCode errorCode, Object details, String message) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    enum ErrorCode {
        PRODUCT_NOT_FOUND
    }
}
