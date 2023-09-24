package com.trafilea.coffeeshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequestAuthenticationException extends RuntimeException {
    public RequestAuthenticationException(String message) {
        super(message);
    }
}
