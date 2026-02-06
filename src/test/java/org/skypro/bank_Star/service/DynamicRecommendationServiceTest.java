package org.skypro.bank_Star.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.bank_Star.entity.DynamicRecommendation;
import org.skypro.bank_Star.entity.DynamicRule;
import org.skypro.bank_Star.repository.DynamicRecommendationsRepositiry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DynamicRecommendationServiceTest {

    @Mock
    private DynamicRecommendationsRepositiry dynamicRecommendationsRepositiry;

    @InjectMocks
    private DynamicRecommendationService dynamicRecommendationService;

    @Test
    void createDynamicRecommendationTest() {

        DynamicRecommendation recommendation = new DynamicRecommendation();
        recommendation.setProductName("Test product");
        recommendation.setProductId(UUID.randomUUID());
        recommendation.setProductText("Test text");
        List<DynamicRule> rules = new ArrayList<>();
        rules.add(new DynamicRule());
        recommendation.setDynamicRule(rules);

        when(dynamicRecommendationsRepositiry.save(any(DynamicRecommendation.class)))
                .thenReturn(recommendation);

        DynamicRecommendation result = dynamicRecommendationService.createDynamicRecommendation(recommendation);

        assertEquals(recommendation.getProductName(), "Test product");
        assertEquals(recommendation.getProductText(),"Test text");
        verify(dynamicRecommendationsRepositiry).save(recommendation);
    }

    @Test
    void deleteDynamicRecommendationTest() {

        dynamicRecommendationService.deleteDynamicRecommendation(1L);

        verify(dynamicRecommendationsRepositiry).deleteById(1L);
    }
}
