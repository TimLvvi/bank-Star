package org.skypro.bank_Star.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.model.RecommendationResponse;
import org.skypro.bank_Star.service.RecommendationService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RecommendationsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private RecommendationsController recommendationsController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationsController).build();
    }

    @Test
    void getRecommendationTest() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Recommendation recommendation = new Recommendation("Test", productId, "Test text");
        RecommendationResponse response = new RecommendationResponse(userId, Arrays.asList(recommendation));

        when(recommendationService.getRecommendation(any(UUID.class))).thenReturn(response);


        mockMvc.perform(get("/recommendation/{user_id}", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(userId.toString()))
                .andExpect(jsonPath("$.recommendations[0].name").value("Test"));
    }
}