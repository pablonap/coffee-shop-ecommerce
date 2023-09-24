package com.trafilea.coffeeshop.exception;

public enum ExceptionMessages {
    RESOURCE_NOT_FOUND("Resource not found"),
    AUTHENTICATION_ERROR(
            "Username not found"),
    CART_STATE_EXCEPTION(
            "Cart is in finished state"),
    PROMOTION_APPLICATION_EXCEPTION(
            "Promotion cannot be applied");

    private final String message;

    private ExceptionMessages(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
