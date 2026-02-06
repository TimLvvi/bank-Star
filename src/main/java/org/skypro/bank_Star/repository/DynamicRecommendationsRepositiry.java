package org.skypro.bank_Star.repository;

import org.skypro.bank_Star.entity.DynamicRecommendation;
import org.skypro.bank_Star.model.RecommendationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с рекомендациями и правилами банка.
 */
public interface DynamicRecommendationsRepositiry extends JpaRepository<DynamicRecommendation, Long> {
}
