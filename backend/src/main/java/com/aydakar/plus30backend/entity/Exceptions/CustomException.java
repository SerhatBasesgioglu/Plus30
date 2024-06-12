package com.aydakar.plus30backend.entity.Exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int httpStatusCode;

    public CustomException(int httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

}


