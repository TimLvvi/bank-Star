package org.skypro.bank_Star.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }


    public boolean hasProductType(UUID userId, String productType) {
        String sql = """
                    SELECT COUNT(*) > 0 
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = ?
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType);
    }

    public int getTotalDepositsByProductType(UUID userId, String productType) {
        String sql = """
                    SELECT SUM(t.AMOUNT)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'DEPOSIT'
                """;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType);
        return result != null ? result : 0;
    }

    public int getTotalWithdrawByProductType(UUID userId, String productType) {
        String sql = """
                    SELECT SUM(t.AMOUNT)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'WITHDRAW'
                """;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType);
        return result != null ? result : 0;
    }


}

