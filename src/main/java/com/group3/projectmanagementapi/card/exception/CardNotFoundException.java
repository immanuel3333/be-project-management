package com.group3.projectmanagementapi.card.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Card Not Found")
public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException() {

    }

    public CardNotFoundException(String message) {
        super(message);
    }

}
