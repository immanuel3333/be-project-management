package com.group3.projectmanagementapi.status.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Status Not Found")
public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException() {

    }

    public StatusNotFoundException(String message) {
        super(message);
    }
}
