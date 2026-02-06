package org.skypro.bank_Star.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.bank_Star.entity.DynamicRule;
import org.skypro.bank_Star.entity.StatisticsRule;
import org.skypro.bank_Star.repository.StatisticsRuleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsRuleServiceTest {

    @Mock
    private StatisticsRuleRepository statisticsRuleRepository;

    @InjectMocks
    private StatisticsRuleService statisticsRuleService;

    @Test
    void incrementRuleCounterTest() {

        DynamicRule rule = new DynamicRule();
        rule.setId(1L);

        StatisticsRule existingStats = new StatisticsRule();
        existingStats.setCount(5L);

        when(statisticsRuleRepository.findByDynamicRuleId(anyLong()))
                .thenReturn(Optional.of(existingStats));


        statisticsRuleService.incrementRuleCounter(rule);


        assertEquals(6L, existingStats.getCount());
        verify(statisticsRuleRepository).save(existingStats);
    }
}