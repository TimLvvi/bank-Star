package org.skypro.bank_Star.service;


import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.model.RecommendationResponse;
import org.skypro.bank_Star.rules.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Основной сервис для получения рекомендаций банковских продуктов.
 * Формирует финальный ответ.
 */


@Service
public class RecommendationService {
    private RecommendationRuleSet recommendationRuleSet;

    public RecommendationService(RecommendationRuleSet recommendationRuleSet) {
        this.recommendationRuleSet = recommendationRuleSet;
    }

    /**
     * Получает персонализированные рекомендации для указанного пользователя.
     */

    public RecommendationResponse getRecommendation(UUID userId) {
        List<Recommendation> recommendations = recommendationRuleSet.getRecommendation(userId);
        return new RecommendationResponse(userId, recommendations);
    }
}

