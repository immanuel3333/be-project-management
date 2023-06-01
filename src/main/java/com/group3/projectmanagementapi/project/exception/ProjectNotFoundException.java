package com.group3.projectmanagementapi.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Project Not Found")
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {

    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
