package com.trading212.cryptocurrencytrading.exception;

public class NotExistingCryptoException extends RuntimeException {

    public NotExistingCryptoException(String message) {
        super(message);
    }

    public NotExistingCryptoException(String message, Throwable cause) {
        super(message, cause);
    }

}
