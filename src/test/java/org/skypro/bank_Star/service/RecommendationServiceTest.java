package org.skypro.bank_Star.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.model.RecommendationResponse;
import org.skypro.bank_Star.rules.RecommendationRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock
    private RecommendationRuleSet recommendationRuleSet;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void getRecommendationTest() {

        UUID userId = UUID.randomUUID();
        List<Recommendation> recommendations = new ArrayList<>();
             recommendations.add(new Recommendation("Test", UUID.randomUUID(), "Test text"));

        when(recommendationRuleSet.getRecommendation(userId)).thenReturn(recommendations);


        RecommendationResponse response = recommendationService.getRecommendation(userId);


        assertEquals(userId, response.getUser_id());
        assertEquals(1, response.getRecommendations().size());
        assertEquals("Test", response.getRecommendations().get(0).getName());
    }
}