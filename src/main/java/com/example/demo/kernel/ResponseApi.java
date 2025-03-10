package com.example.demo.kernel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApi<T> {
    private T data;
    private HttpStatus status;
    private boolean error;
    private String message;

    public ResponseApi(HttpStatus status, boolean error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ResponseApi(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }
    public ResponseApi(HttpStatus status) {
        this.status = status;
    }

    public ResponseApi(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}