package com.aydakar.plus30backend.entity;

import lombok.Data;

import java.util.Map;

@Data
public class ApiError {
    private String errorCode;
    private int httpStatus;
    private Map<String, Object> implementationDetails;
    private String message;
}
