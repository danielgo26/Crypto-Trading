package com.trading212.cryptocurrencytrading.wallet.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto_wallet_entities")
public class CryptoWalletEntity {

    @Id
    private Long cryptoWalletEntityId;

    private Long traderId;
    private String cryptoCurrencyName;
    private String cryptoCurrencySymbol;
    private BigDecimal amount;
    private LocalDateTime lastModified;

}