package com.aydakar.plus30backend.entity.Exceptions;

import com.aydakar.plus30backend.entity.ApiError;
import lombok.Getter;

import java.util.Map;

@Getter
public class ApiResponseException extends RuntimeException {
    private final String errorCode;
    private final int httpStatus;
    private final Map<String, Object> implementationDetails;

    public ApiResponseException(ApiError apiError) {
        super(apiError.getMessage());
        this.errorCode = apiError.getErrorCode();
        this.httpStatus = apiError.getHttpStatus();
        this.implementationDetails = apiError.getImplementationDetails();
    }
}


