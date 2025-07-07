package com.example.springboot.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.util.Map;

@Getter
@Setter
public class MyErrorResponse implements ErrorResponse {
    private String message;
    private HttpStatus status;
    private Map<String, String> dtoValidationErrors;

    public MyErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.dtoValidationErrors = null;
    }

    public MyErrorResponse(String message, HttpStatus status, Map<String, String> dtoValidationErrors) {
        this.message = message;
        this.status = status;
        this.dtoValidationErrors = dtoValidationErrors;
    }

    @Override
    public HttpStatusCode getStatusCode(  ) {
        return status;
    }

    @Override
    public ProblemDetail getBody() {
        return null;
    }
}
