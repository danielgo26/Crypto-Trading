package com.trading212.cryptocurrencytrading.trader.repository;

import com.trading212.cryptocurrencytrading.trader.model.Trader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class TraderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TraderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Trader save(Trader trader) {
        String sql = "INSERT INTO traders (first_name, last_name, email, balance) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, trader.getFirstName(), trader.getLastName(), trader.getEmail(), trader.getBalance());

        trader.setId(getTraderId(trader));
        return trader;
    }

    public Trader update(Trader trader) {
        String sql = "UPDATE traders SET first_name = ?, last_name = ?, email = ?, balance = ? WHERE id = ?";

        jdbcTemplate.update(sql, trader.getFirstName(), trader.getLastName(),
            trader.getEmail(), trader.getBalance(), trader.getId());

        return trader;
    }

    public Trader getTraderById(Long id) {
        String sql = "SELECT t.id, t.first_name, t.last_name, t.email, t.balance " +
            "FROM traders t WHERE t.id = ?";

        return jdbcTemplate.queryForObject(sql, getTraderRowMapper(), id);
    }

    public List<Trader> getAllTraders() {
        String sql = "SELECT * FROM traders ORDER BY id";

        return jdbcTemplate.query(sql, getTraderRowMapper());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM traders WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    private Long getTraderId(Trader trader) {
        String findIdSql = "SELECT id FROM traders WHERE email = ?";

        return jdbcTemplate.queryForObject(findIdSql, Long.class, trader.getEmail());
    }

    private RowMapper<Trader> getTraderRowMapper() {
        return (rs, rowNum) -> {
            Long traderId = rs.getLong("id");

            return new Trader(
                traderId,
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getBigDecimal("balance"),
                new ArrayList<>(),
                new LinkedHashMap<>()
            );
        };
    }

}