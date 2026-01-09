package com.trading212.cryptocurrencytrading.trader.model;

import com.trading212.cryptocurrencytrading.wallet.model.CryptoWalletEntity;
import com.trading212.cryptocurrencytrading.transaction.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "traders")
public class Trader {

    public static final BigDecimal INITIAL_BALANCE = new BigDecimal("10000");

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal balance = INITIAL_BALANCE;
    private List<Transaction> transactions = new ArrayList<>();
    private Map<String, CryptoWalletEntity> cryptoWallet = new LinkedHashMap<>();

}