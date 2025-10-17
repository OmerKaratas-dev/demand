package com.example.demand.controller;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = org.springframework.http.HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Resource Not Found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
