package com.trading212.cryptocurrencytrading.exception;

public class NotExistingTraderException extends RuntimeException {

    public NotExistingTraderException(String message) {
        super(message);
    }

    public NotExistingTraderException(String message, Throwable cause) {
        super(message, cause);
    }

}
