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

    public boolean userOf(UUID userId, String productType, boolean negate) {
        String sql = """
                    SELECT COUNT(*) > 0 
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = ?
                """;


        if (negate == false) {
            return jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType);
        }
        else {
            return !jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType);
        }
    }

    public boolean activeUserOf(UUID userId, String productType, boolean negate) {
        String sql = """
                    SELECT COUNT(*) >=5 
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = ?
                """;
        if (negate == false) {
            return jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType);
        } else {
            return !jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType);
        }
    }

    public boolean transactionSumCompareDepositWithdraw(UUID userId, String productType, String operator, boolean negate) {
        String sql = """
                    SELECT COALESCE(SUM(t.AMOUNT),0)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'DEPOSIT'
                """;
       int sumAmountDeposit = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType);

        String sql1 = """
                    SELECT COALESCE(SUM(t.AMOUNT),0)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'WITHDRAW'
                """;
        int sumAmountWithdraw = jdbcTemplate.queryForObject(sql1, Integer.class, userId, productType);

        if (negate == false) {
            if (operator.equals(">")) {
                return sumAmountDeposit > sumAmountWithdraw;}

            if (operator.equals("<")) {
                return sumAmountDeposit < sumAmountWithdraw;}

            if (operator.equals("=")) {
                return sumAmountDeposit == sumAmountWithdraw;}

            if (operator.equals(">=")) {
                return sumAmountDeposit >= sumAmountWithdraw;}

            if (operator.equals("<=")) {
                return sumAmountDeposit <= sumAmountWithdraw;}
        } else {
            if (operator.equals(">")) {
                return !(sumAmountDeposit > sumAmountWithdraw);}

            if (operator.equals("<")) {
                return !(sumAmountDeposit < sumAmountWithdraw);}

            if (operator.equals("=")) {
                return !(sumAmountDeposit == sumAmountWithdraw);}

            if (operator.equals(">=")) {
                return !(sumAmountDeposit >= sumAmountWithdraw);}

            if (operator.equals("<=")) {
                return !(sumAmountDeposit <= sumAmountWithdraw);}
        }
        return false;
        }


    public boolean transactionSumCompare(UUID userId, String productType, String trasactionType,String operator, int money, boolean negate) {
        String sql = """
                    SELECT COALESCE(SUM(t.AMOUNT),0)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = ?
                """;
        int sumAmount = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType, trasactionType);

        if (negate == false) {
            if (operator.equals(">")) {
                return sumAmount > money;}

            if (operator.equals("<")) {
                return sumAmount < money;}

            if (operator.equals("=")) {
                return sumAmount == money;}

            if (operator.equals(">=")) {
                return sumAmount >= money;}

            if (operator.equals("<=")) {
                return sumAmount <= money;}
        } else {
            if (operator.equals(">")) {
                return !(sumAmount > money);}

            if (operator.equals("<")) {
                return !(sumAmount < money);}

            if (operator.equals("=")) {
                return !(sumAmount == money);}

            if (operator.equals(">=")) {
                return !(sumAmount >= money);}

            if (operator.equals("<=")) {
                return !(sumAmount <= money);}
        }
        return false;
    }

}



