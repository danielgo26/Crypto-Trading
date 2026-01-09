package com.trading212.cryptocurrencytrading.crypto.service;

import com.trading212.cryptocurrencytrading.crypto.model.CryptoCurrency;
import com.trading212.cryptocurrencytrading.exception.CryptoCurrenciesFileLoadException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Map;

@Getter
@Service
public class CryptoService {

    private static final String PATH_TO_RESOURCES = "src/main/resources/supported_crypto_pairs.csv";

    private final Map<String, CryptoCurrency> cryptoCurrenciesMap = new HashMap<>();

    @PostConstruct
    public void loadCryptoCurrenciesMap() {
        try {
            File file = ResourceUtils.getFile(PATH_TO_RESOURCES);
            List<String> lines = Files.readAllLines(Paths.get(file.toURI()));

            for (String line : lines) {
                String cryptoSymbol = line.split("-")[1];
                cryptoCurrenciesMap.put(cryptoSymbol, CryptoCurrency.from(line));
            }
        } catch (IOException e) {
            throw new CryptoCurrenciesFileLoadException("Error loading cryptos from file: " + e.getMessage());
        }
    }

}
