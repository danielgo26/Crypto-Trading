package com.trading212.cryptocurrencytrading.crypto.controller;

import com.trading212.cryptocurrencytrading.api.kraken.model.KrakenResponse;
import com.trading212.cryptocurrencytrading.api.kraken.service.KrakenWebSocketService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/cryptos")
public class CryptoDataController {

    private final KrakenWebSocketService krakenWebSocketService;

    public CryptoDataController(KrakenWebSocketService krakenWebSocketService) {
        this.krakenWebSocketService = krakenWebSocketService;
    }

    @GetMapping
    public Map<String, KrakenResponse.Ticker> getCryptoTickers() {
        return krakenWebSocketService.getTickerData();
    }

}
