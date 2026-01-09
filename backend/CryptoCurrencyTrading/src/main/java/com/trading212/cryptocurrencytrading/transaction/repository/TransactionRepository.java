package com.trading212.cryptocurrencytrading.transaction.repository;

import com.trading212.cryptocurrencytrading.transaction.model.Transaction;
import com.trading212.cryptocurrencytrading.transaction.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transaction> getAllTransactionsByTraderId(Long traderId) {
        String sql = "SELECT * FROM transactions WHERE trader_id = ?";

        RowMapper<Transaction> transactionRowMapper = getTransactionRowMapper(traderId);

        return jdbcTemplate.query(sql, transactionRowMapper, traderId);
    }

    public Transaction saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (trader_id, amount, crypto_currency_traded_price, " +
            "crypto_currency_name, crypto_currency_symbol, profit, transaction_type, transaction_date) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, transaction.getTraderId(), transaction.getAmount(),
            transaction.getCryptoCurrencyTradedPrice(), transaction.getCryptoCurrencyName(),
            transaction.getCryptoCurrencySymbol(), transaction.getProfit(),
            transaction.getTransactionType().name(), transaction.getTransactionDate());

        transaction.setId(getTransactionId(transaction));
        return transaction;
    }

    public void deleteAllTraderTransactions(Long traderId) {
        String sql = "DELETE FROM transactions WHERE trader_id = ?";

        jdbcTemplate.update(sql, traderId);
    }

    private RowMapper<Transaction> getTransactionRowMapper(Long traderId) {
        return (rs, rowNum) -> {
            Long transactionId = rs.getLong("transaction_id");
            BigDecimal amount = rs.getBigDecimal("amount");
            BigDecimal cryptoCurrencyTradedPrice = rs.getBigDecimal("crypto_currency_traded_price");
            String cryptoCurrencyName = rs.getString("crypto_currency_name");
            String cryptoCurrencySymbol = rs.getString("crypto_currency_symbol");
            BigDecimal profit = rs.getBigDecimal("profit");
            TransactionType transactionType = TransactionType.valueOf(rs.getString("transaction_type").toUpperCase());
            LocalDateTime transactionDate = rs.getTimestamp("transaction_date").toLocalDateTime();

            return new Transaction(transactionId, traderId, amount, cryptoCurrencyTradedPrice,
                cryptoCurrencyName, cryptoCurrencySymbol, profit, transactionType, transactionDate);
        };
    }

    private Long getTransactionId(Transaction transaction) {
        String findIdSql = "SELECT transaction_id FROM transactions WHERE transaction_date = ?";

        return jdbcTemplate.queryForObject(findIdSql, Long.class, transaction.getTransactionDate());
    }

}