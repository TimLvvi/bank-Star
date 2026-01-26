package org.skypro.bank_Star.rules;

import org.skypro.bank_Star.entity.DynamicRecommendation;
import org.skypro.bank_Star.entity.DynamicRule;
import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.repository.DynamicRecommendationsRepositiry;
import org.skypro.bank_Star.repository.RecommendationsRepository;
import org.skypro.bank_Star.service.DynamicRecommendationService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DynamicRuleSet {
    private DynamicRecommendationsRepositiry dynamicRecommendationsRepositiry;
    private RecommendationsRepository recommendationsRepository;

    public DynamicRuleSet(DynamicRecommendationsRepositiry dynamicRecommendationsRepositiry, RecommendationsRepository recommendationsRepository) {
        this.dynamicRecommendationsRepositiry = dynamicRecommendationsRepositiry;
        this.recommendationsRepository = recommendationsRepository;
    }

    public List<Recommendation> getDynamicRecommendation(UUID userId) {
        List<DynamicRecommendation> allDynamicRecommendation = dynamicRecommendationsRepositiry.findAll();
        List<Recommendation> resultRecommendationList = new ArrayList<>();

        for (DynamicRecommendation dynamicRecommendation : allDynamicRecommendation) {

            List<Boolean> resultBoolean = new ArrayList<>();

            for (DynamicRule dynamicRule : dynamicRecommendation.getDynamicRule()) {
                if (dynamicRule.getQuery().equals("USER_OF")) {
                    boolean result = recommendationsRepository.userOf(userId, dynamicRule.getArguments().get(0), dynamicRule.getNegate());
                    resultBoolean.add(result);
                }
            }

            for (DynamicRule dynamicRule : dynamicRecommendation.getDynamicRule()) {
                if (dynamicRule.getQuery().equals("ACTIVE_USER_OF")) {
                    boolean result = recommendationsRepository.activeUserOf(userId, dynamicRule.getArguments().get(0), dynamicRule.getNegate());
                    resultBoolean.add(result);
                }
            }

            for (DynamicRule dynamicRule : dynamicRecommendation.getDynamicRule()) {
                if (dynamicRule.getQuery().equals("TRANSACTION_SUM_COMPARE")) {
                    boolean result = recommendationsRepository.transactionSumCompare(userId, dynamicRule.getArguments().get(0),dynamicRule.getArguments().get(1),dynamicRule.getArguments().get(2),Integer.parseInt(dynamicRule.getArguments().get(3)), dynamicRule.getNegate());
                    resultBoolean.add(result);
                }
            }

            for (DynamicRule dynamicRule : dynamicRecommendation.getDynamicRule()) {
                if (dynamicRule.getQuery().equals("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")) {
                    boolean result = recommendationsRepository.transactionSumCompareDepositWithdraw(userId, dynamicRule.getArguments().get(0),dynamicRule.getArguments().get(1), dynamicRule.getNegate());
                    resultBoolean.add(result);
                }
            }

            if (!resultBoolean.contains(false)) {
                Recommendation result = new Recommendation(dynamicRecommendation.getProductName(),dynamicRecommendation.getProductId(),dynamicRecommendation.getProductText());
                resultRecommendationList.add(result);
            }

        }
        return resultRecommendationList;
    }
}
