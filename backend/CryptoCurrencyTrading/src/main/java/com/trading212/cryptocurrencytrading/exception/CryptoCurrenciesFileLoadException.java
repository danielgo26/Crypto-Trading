package com.trading212.cryptocurrencytrading.exception;

public class CryptoCurrenciesFileLoadException extends RuntimeException {

    public CryptoCurrenciesFileLoadException(String message) {
        super(message);
    }

    public CryptoCurrenciesFileLoadException(String message, Throwable cause) {
        super(message, cause);
    }

}
