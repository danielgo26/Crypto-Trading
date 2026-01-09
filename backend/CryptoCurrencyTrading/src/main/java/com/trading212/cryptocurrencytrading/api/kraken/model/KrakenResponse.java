package com.trading212.cryptocurrencytrading.api.kraken.model;

import com.trading212.cryptocurrencytrading.crypto.model.CryptoCurrency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class KrakenResponse {

    private String channel;
    private String type;
    private List<Ticker> data;

    @Getter
    @Setter
    public static class Ticker {

        //extra properties
        private String fullName;
        private String cryptoSymbol;
        private String currencyName;

        //regular response properties
        private String symbol;
        private BigDecimal bid;
        private BigDecimal bid_qty;
        private BigDecimal ask;
        private BigDecimal ask_qty;
        private BigDecimal last;
        private BigDecimal volume;
        private BigDecimal vwap;
        private BigDecimal low;
        private BigDecimal high;
        private BigDecimal change;
        private BigDecimal change_pct;

        public void loadCryptoExtraProperties(Map<String, CryptoCurrency> map) {
            String[] cryptoArgs = symbol.split("/");
            cryptoSymbol = cryptoArgs[0];
            currencyName = cryptoArgs[1];

            if (!map.containsKey(cryptoSymbol)) {
                this.fullName = "Unknown";
            } else {
                this.fullName = map.get(cryptoSymbol).fullName();
            }
        }

    }

}
