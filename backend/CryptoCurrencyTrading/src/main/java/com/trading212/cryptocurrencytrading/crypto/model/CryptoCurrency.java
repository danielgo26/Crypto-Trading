package com.trading212.cryptocurrencytrading.crypto.model;

public record CryptoCurrency(String fullName, String symbol, String currencyName) {

    public static CryptoCurrency from(String line) {
        String[] args = line.split("-");

        return new CryptoCurrency(args[0], args[1], args[2]);
    }

}