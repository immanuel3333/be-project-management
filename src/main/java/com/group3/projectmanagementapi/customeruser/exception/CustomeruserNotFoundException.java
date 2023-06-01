package com.group3.projectmanagementapi.customeruser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class CustomeruserNotFoundException extends RuntimeException {
    public CustomeruserNotFoundException() {

    }

    public CustomeruserNotFoundException(String message) {
        super(message);
    }
}
