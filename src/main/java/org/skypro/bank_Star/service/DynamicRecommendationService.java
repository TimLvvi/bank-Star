package org.skypro.bank_Star.service;

import jakarta.transaction.Transactional;
import org.skypro.bank_Star.entity.DynamicRecommendation;
import org.skypro.bank_Star.entity.DynamicRule;
import org.skypro.bank_Star.repository.DynamicRecommendationsRepositiry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicRecommendationService {
    private DynamicRecommendationsRepositiry dynamicRecommendationsRepositiry;

    public DynamicRecommendationService(DynamicRecommendationsRepositiry dynamicRecommendationsRepositiry) {
        this.dynamicRecommendationsRepositiry = dynamicRecommendationsRepositiry;
    }

    @Transactional
    public DynamicRecommendation createDynamicRecommendation(DynamicRecommendation dynamicRecommendation) {
        if (dynamicRecommendation.getDynamicRule() != null) {
            for (DynamicRule rule : dynamicRecommendation.getDynamicRule()) {
                rule.setDynamicRecommendation(dynamicRecommendation);
            }
        }
        return dynamicRecommendationsRepositiry.save(dynamicRecommendation);
    }

    public void deleteDynamicRecommendation(long id) {
        dynamicRecommendationsRepositiry.deleteById(id);
    }

    public List<DynamicRecommendation> getAll() {
       return dynamicRecommendationsRepositiry.findAll();
    }
}
