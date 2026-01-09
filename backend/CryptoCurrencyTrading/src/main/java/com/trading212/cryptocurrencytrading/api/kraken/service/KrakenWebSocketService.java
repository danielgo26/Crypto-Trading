package com.trading212.cryptocurrencytrading.api.kraken.service;

import com.google.gson.Gson;
import com.trading212.cryptocurrencytrading.crypto.service.CryptoService;
import com.trading212.cryptocurrencytrading.crypto.model.CryptoCurrency;
import com.trading212.cryptocurrencytrading.api.kraken.model.KrakenResponse;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KrakenWebSocketService implements WebSocket.Listener {

    private static final Integer CONNECTION_TIMEOUT_DELAY = 10;
    private static final String API_CONNECTION_URI = "wss://ws.kraken.com/v2";
    private static final String API_CONNECTION_STRING = """
                {
                  "method": "%s",
                  "params": {
                    "channel": "ticker",
                    "symbol": [%s]
                  }
                }
                """;

    @Getter
    private final Map<String, KrakenResponse.Ticker> tickerData = new ConcurrentHashMap<>();

    private final CryptoService cryptoPairService;
    private WebSocket webSocket;
    private String cryptoPairsListToSubscribeFor;
    private Map<String, CryptoCurrency> cryptoCurrenciesMap = new HashMap<>() ;

    private boolean subscribed = false;

    public KrakenWebSocketService(CryptoService cryptoPairService) {
        this.cryptoPairService = cryptoPairService;
        loadCryptoCurrencies();
    }

    @PostConstruct
    public void connect() {
        HttpClient client = HttpClient.newHttpClient();

        client.newWebSocketBuilder()
            .connectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT_DELAY))
            .buildAsync(URI.create(API_CONNECTION_URI), this)
            .thenAccept(webSocket -> {
                this.webSocket = webSocket;
                sendSubscriptionMessage();
            });
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        if (!subscribed) {
            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        String message = data.toString();
        Gson gson = new Gson();
        KrakenResponse response = gson.fromJson(message, KrakenResponse.class);

        if (response.getData() != null) {
            for (KrakenResponse.Ticker ticker : response.getData()) {
                ticker.loadCryptoExtraProperties(cryptoCurrenciesMap);
                tickerData.put(ticker.getCryptoSymbol(), ticker);
            }
        }

        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @PreDestroy
    public void disconnect() {
        if (webSocket != null) {
            String unsubscribeMessage = String.format(API_CONNECTION_STRING, "unsubscribe", cryptoPairsListToSubscribeFor);

            webSocket.sendText(unsubscribeMessage, true)
                .thenRun(() -> {
                    webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Shutting down");
                });
        }
    }

    private void loadCryptoCurrencies() {
        this.cryptoCurrenciesMap = cryptoPairService.getCryptoCurrenciesMap();

        this.cryptoPairsListToSubscribeFor = this.cryptoCurrenciesMap.values().stream()
            .map(cryptoCurrency -> "\"" + cryptoCurrency.symbol() + "/" + cryptoCurrency.currencyName() + "\"")
            .collect(Collectors.joining(","));
    }

    private void sendSubscriptionMessage() {
        String subscriptionMessage = String.format(API_CONNECTION_STRING, "subscribe", cryptoPairsListToSubscribeFor);

        webSocket.sendText(subscriptionMessage, true)
            .thenRun(() -> {
                subscribed = true;
            });
    }

}
