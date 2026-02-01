package org.skypro.bank_Star.service;

import org.skypro.bank_Star.entity.DynamicRule;
import org.skypro.bank_Star.entity.StatisticsRule;
import org.skypro.bank_Star.repository.StatisticsRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsRuleService {
    private StatisticsRuleRepository statisticsRuleRepository;

    public StatisticsRuleService(StatisticsRuleRepository statisticsRuleRepository) {
        this.statisticsRuleRepository = statisticsRuleRepository;
    }

    public List<StatisticsRule> getAll() {
        return statisticsRuleRepository.findAll();
    }

    public void incrementRuleCounter(DynamicRule ruleId) {
        StatisticsRule stats = statisticsRuleRepository.findByDynamicRuleId(ruleId.getId())
                .orElseGet(() -> {

                    StatisticsRule newStats = new StatisticsRule();
                    newStats.setCount(0L);
                    newStats.setDynamicRule(ruleId);

                    return statisticsRuleRepository.save(newStats);
                });
        stats.setCount(stats.getCount() + 1);
        statisticsRuleRepository.save(stats);
    }
}


