package org.skypro.bank_Star.repository;

import org.skypro.bank_Star.entity.DynamicRecommendation;
import org.skypro.bank_Star.entity.StatisticsRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы со статистикой.
 */
public interface StatisticsRuleRepository extends JpaRepository<StatisticsRule, Long> {
    Optional<StatisticsRule> findByDynamicRuleId(Long ruleId);
}

