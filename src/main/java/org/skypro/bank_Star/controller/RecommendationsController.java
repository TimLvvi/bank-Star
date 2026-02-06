package org.skypro.bank_Star.controller;

import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.model.RecommendationResponse;
import org.skypro.bank_Star.service.RecommendationService;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

/**
 * REST контроллер для получения рекомендаций.
 * Основной endpoint для клиентских приложений.
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {

    private final RecommendationService recommendationService;

    public RecommendationsController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Возвращает персонализированные рекомендации для пользователя.
     */
    @GetMapping("/{user_id}")
    public RecommendationResponse getRecommendation(@PathVariable("user_id") UUID userId) {
        return recommendationService.getRecommendation(userId);
    }
}
