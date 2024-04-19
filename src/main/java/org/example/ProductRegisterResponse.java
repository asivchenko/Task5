package org.example;

import org.springframework.http.HttpStatus;

public class ProductRegisterResponse {
    private String message;
    private HttpStatus status;
    public ProductRegisterResponse (String message, HttpStatus status)
    {
        this.message=message;
        this.status=status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
