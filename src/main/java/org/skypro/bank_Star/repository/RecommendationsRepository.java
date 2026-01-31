package org.skypro.bank_Star.repository;




import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Cache<String,Boolean> userOfCache;
    private final Cache<String,Boolean> activeUserOfCache;
    private final Cache<String,Boolean> transactionSumCompareDepositWithdrawCache;
    private final Cache<String,Boolean> transactionSumCompareCache;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userOfCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build();
        this.activeUserOfCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build();
        this.transactionSumCompareDepositWithdrawCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build();
        this.transactionSumCompareCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build();

    }

    public boolean userOf(UUID userId, String productType, boolean negate) {
        String cacheKey = userId.toString() + "|" +productType +"|" + negate;

        return userOfCache.get(cacheKey,key->{
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
    });}

    public boolean activeUserOf(UUID userId, String productType, boolean negate) {
        String cacheKey = userId.toString() + "|" + productType + "|" + negate;
        return activeUserOfCache.get(cacheKey, key -> {
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
    });}

    public boolean transactionSumCompareDepositWithdraw(UUID userId, String productType, String operator, boolean negate) {
        String cacheKey = userId.toString() + "|" + productType + "|" + operator + "|" + negate;
        return transactionSumCompareDepositWithdrawCache.get(cacheKey, key -> {
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
        });}


    public boolean transactionSumCompare(UUID userId, String productType, String trasactionType,String operator, int money, boolean negate) {
        String cacheKey = userId.toString() + "|" + productType + "|" + trasactionType + "|" + operator + "|" + money + "|" + negate;
        return transactionSumCompareCache.get(cacheKey, key -> {
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
    });}

    public UUID findUserId(String userName){
        String sql = """
                SELECT ID 
                FROM USERS 
                WHERE USERNAME = ? 
                """;

        try {
            return jdbcTemplate.queryForObject(sql, UUID.class, userName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    };

    public String findUserFirstName(String userName){
        String sql = """
                SELECT FIRST_NAME 
                FROM USERS 
                WHERE USERNAME = ? 
                """;

            return jdbcTemplate.queryForObject(sql, String.class, userName);

    };

    public String findUserLasttName(String userName){
        String sql = """
                SELECT LAST_NAME 
                FROM USERS 
                WHERE USERNAME = ? 
                """;

        return jdbcTemplate.queryForObject(sql, String.class, userName);

    };

    public void clearCache() {
        userOfCache.invalidateAll();
        activeUserOfCache.invalidateAll();
        transactionSumCompareDepositWithdrawCache.invalidateAll();
        transactionSumCompareCache.invalidateAll();
    }
}



