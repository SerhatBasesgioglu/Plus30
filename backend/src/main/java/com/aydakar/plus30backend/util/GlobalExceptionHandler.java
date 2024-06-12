package com.aydakar.plus30backend.util;

import com.aydakar.plus30backend.entity.Exceptions.ApiResponseException;
import com.aydakar.plus30backend.entity.Exceptions.CustomException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiResponseException.class)
    public ResponseEntity<Object> handleApiResponseException(ApiResponseException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(("errorCode"), ex.getErrorCode());
        body.put("httpStatus", ex.getHttpStatus());
        body.put(("implementationDetails"), ex.getImplementationDetails());
        body.put("message", ex.getMessage());
        body.put("timeStamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatusCode.valueOf(ex.getHttpStatus()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("httpStatus", ex.getHttpStatusCode());
        body.put("message", ex.getMessage());
        body.put("timeStamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatusCode.valueOf(ex.getHttpStatusCode()));
    }
}
