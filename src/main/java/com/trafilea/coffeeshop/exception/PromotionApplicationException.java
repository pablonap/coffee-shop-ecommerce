package com.trafilea.coffeeshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PromotionApplicationException extends RuntimeException {

    public PromotionApplicationException(String message) {
        super(message);
    }
}
