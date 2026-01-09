package com.trading212.cryptocurrencytrading.transaction.model;

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
@Table(name = "transactions")
public class Transaction {

    @Id
    private Long id;

    private Long traderId;
    private BigDecimal amount;
    private BigDecimal cryptoCurrencyTradedPrice;
    private String cryptoCurrencyName;
    private String cryptoCurrencySymbol;
    private BigDecimal profit;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;

}