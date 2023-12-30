package com.example.patienthub.patienthub.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Data
public class ResponseWrapper<T> {
    @Getter
    private HttpStatus statusCode;
    private HttpStatus status;
    private String message;
    private T data;

    public ResponseWrapper(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
