package com.trading212.cryptocurrencytrading.wallet.repository;

import com.trading212.cryptocurrencytrading.wallet.model.CryptoWalletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class CryptoWalletEntitiesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CryptoWalletEntitiesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long getCryptoWalletEntityId(CryptoWalletEntity cryptoWalletEntity) {
        String findIdSql = "SELECT crypto_wallet_entity_id FROM crypto_wallet_entities " +
            "WHERE trader_id = ? AND crypto_currency_name = ? AND crypto_currency_symbol = ?";

        return jdbcTemplate.queryForObject(findIdSql, Long.class, cryptoWalletEntity.getTraderId(),
            cryptoWalletEntity.getCryptoCurrencyName(), cryptoWalletEntity.getCryptoCurrencySymbol());
    }

    public Map<String, CryptoWalletEntity> getAllCryptoWalletEntities(Long traderId) {
        String sql = "SELECT * FROM crypto_wallet_entities WHERE trader_id = ?";

        RowMapper<CryptoWalletEntity> cryptoWalletEntityRowMapper = getCryptoWalletEntityRowMapper(traderId);

        List<CryptoWalletEntity> cryptoWalletEntities = jdbcTemplate.query(sql, cryptoWalletEntityRowMapper, traderId);

        return cryptoWalletEntities.stream()
            .collect(Collectors.toMap(
                CryptoWalletEntity::getCryptoCurrencyName,
                Function.identity(),
                (existing, replacement) -> replacement,
                LinkedHashMap::new
            ));
    }

    public CryptoWalletEntity save(CryptoWalletEntity cryptoWalletEntity) {
        String insertSql = "INSERT INTO crypto_wallet_entities " +
            "(trader_id, crypto_currency_name, crypto_currency_symbol, amount, last_modified) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(insertSql, cryptoWalletEntity.getTraderId(),
            cryptoWalletEntity.getCryptoCurrencyName(), cryptoWalletEntity.getCryptoCurrencySymbol(),
            cryptoWalletEntity.getAmount(), cryptoWalletEntity.getLastModified());

        Long id = getCryptoWalletEntityId(cryptoWalletEntity);
        cryptoWalletEntity.setCryptoWalletEntityId(id);

        return cryptoWalletEntity;
    }

    public CryptoWalletEntity update(CryptoWalletEntity cryptoWalletEntity) {
        String updateSql =
            "UPDATE crypto_wallet_entities SET amount = ?, last_modified = ? WHERE crypto_wallet_entity_id = ?";

        jdbcTemplate.update(updateSql, cryptoWalletEntity.getAmount(),
            cryptoWalletEntity.getLastModified(), cryptoWalletEntity.getCryptoWalletEntityId());

        return cryptoWalletEntity;
    }

    public void delete(CryptoWalletEntity cryptoWalletEntity) {
        String deleteSql =
            "DELETE FROM crypto_wallet_entities WHERE crypto_wallet_entity_id = ?";

        jdbcTemplate.update(deleteSql, cryptoWalletEntity.getCryptoWalletEntityId());
    }

    public void deleteAllTraderCryptoWalletEntities(Long traderId) {
        String sql = "DELETE FROM crypto_wallet_entities WHERE trader_id = ?";

        jdbcTemplate.update(sql, traderId);
    }

    private RowMapper<CryptoWalletEntity> getCryptoWalletEntityRowMapper(Long traderId) {
        return (rs, rowNum) -> {
            Long cryptoWalletEntityId = rs.getLong("crypto_wallet_entity_id");
            String cryptoCurrencyName = rs.getString("crypto_currency_name");
            String cryptoCurrencySymbol = rs.getString("crypto_currency_symbol");
            BigDecimal amount = rs.getBigDecimal("amount");
            LocalDateTime lastModified = rs.getTimestamp("last_modified").toLocalDateTime();

            return new CryptoWalletEntity(cryptoWalletEntityId, traderId,
                cryptoCurrencyName, cryptoCurrencySymbol, amount, lastModified);
        };
    }

}