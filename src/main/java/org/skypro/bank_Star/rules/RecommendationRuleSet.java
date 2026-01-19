package org.skypro.bank_Star.rules;


import org.skypro.bank_Star.model.Recommendation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RecommendationRuleSet {
    private List<Rule> rules;

    public RecommendationRuleSet(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Recommendation> getRecommendation(UUID userId) {
        List<Recommendation> recommendations = new ArrayList<>();
        for (Rule rule : rules) {
            Recommendation recommendation = rule.getRecommendation(userId);
            if (recommendation != null) {
                recommendations.add(recommendation);
            }
        }
        return recommendations;
    }
}
