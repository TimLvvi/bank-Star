package org.skypro.bank_Star.service;

import jakarta.transaction.Transactional;
import org.skypro.bank_Star.entity.DynamicRecommendation;
import org.skypro.bank_Star.entity.DynamicRule;
import org.skypro.bank_Star.repository.DynamicRecommendationsRepositiry;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления динамическими правилами рекомендаций.
 */

@Service
public class DynamicRecommendationService {
    private DynamicRecommendationsRepositiry dynamicRecommendationsRepositiry;

    public DynamicRecommendationService(DynamicRecommendationsRepositiry dynamicRecommendationsRepositiry) {
        this.dynamicRecommendationsRepositiry = dynamicRecommendationsRepositiry;
    }

    /**
     * Создает новую динамическую рекомендацию.
     */
    @Transactional
    public DynamicRecommendation createDynamicRecommendation(DynamicRecommendation dynamicRecommendation) {
        if (dynamicRecommendation.getDynamicRule() != null) {
            for (DynamicRule rule : dynamicRecommendation.getDynamicRule()) {
                rule.setDynamicRecommendation(dynamicRecommendation);
            }
        }
        return dynamicRecommendationsRepositiry.save(dynamicRecommendation);
    }

    /**
     * Удаляет динамическую рекомендацию по идентификатору.
     */
    public void deleteDynamicRecommendation(long id) {
        dynamicRecommendationsRepositiry.deleteById(id);
    }

    /**
     * Возвращает список всех динамических рекомендаций.
     */
    public List<DynamicRecommendation> getAll() {
        return dynamicRecommendationsRepositiry.findAll();
    }
}
