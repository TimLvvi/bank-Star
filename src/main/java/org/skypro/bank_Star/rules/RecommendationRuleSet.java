package org.skypro.bank_Star.rules;


import org.skypro.bank_Star.model.Recommendation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RecommendationRuleSet {
    private List<Rule> rules;
    private DynamicRuleSet dynamicRuleSet;

    public RecommendationRuleSet(List<Rule> rules,DynamicRuleSet dynamicRuleSet) {
        this.rules = rules;
        this.dynamicRuleSet = dynamicRuleSet;
    }

    public List<Recommendation> getRecommendation(UUID userId) {
        List<Recommendation> recommendations = new ArrayList<>();
        for (Rule rule : rules) {
            Recommendation recommendation = rule.getRecommendation(userId);
            if (recommendation != null) {
                recommendations.add(recommendation);
            }
        }
        List<Recommendation> dynamicRecommendation = dynamicRuleSet.getDynamicRecommendation(userId);
        recommendations.addAll(dynamicRecommendation);
        return recommendations;
    }
}
