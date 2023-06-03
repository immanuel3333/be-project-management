package com.group3.projectmanagementapi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.group3.projectmanagementapi.card.exception.CardNotFoundException;
import com.group3.projectmanagementapi.customeruser.exception.CustomeruserNotFoundException;
import com.group3.projectmanagementapi.project.exception.ProjectNotFoundException;
import com.group3.projectmanagementapi.status.exception.StatusNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity<Map<String, String>>
    // handleValidationErrors(MethodArgumentNotValidException ex) {
    // List<String> listStringErrorField =
    // ex.getBindingResult().getFieldErrors().stream().map(FieldError::getField)
    // .collect(Collectors.toList());
    // List<String> errors = ex.getBindingResult().getFieldErrors()
    // .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
    // return new ResponseEntity<Map<String, String>>(getErrorsMap(errors,
    // listStringErrorField),
    // new HttpHeaders(),
    // HttpStatus.BAD_REQUEST);
    // }

    // private Map<String, String> getErrorsMap(List<String> errors, List<String>
    // errorFields) {
    // Map<String, String> errorResponse = new HashMap<>();
    // int index = 0;
    // for (String field : errorFields) {
    // errorResponse.put(field, errors.get(index));
    // index++;
    // }
    // System.out.println(errors);
    // return errorResponse;
    // }

    @ExceptionHandler(value = { ProjectNotFoundException.class, CardNotFoundException.class,
            CustomeruserNotFoundException.class, ProjectNotFoundException.class, StatusNotFoundException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException exception, WebRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        return super.handleExceptionInternal(exception, error, new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return super.handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}