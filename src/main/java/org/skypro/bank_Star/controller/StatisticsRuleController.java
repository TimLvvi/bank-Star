package org.skypro.bank_Star.controller;

import org.skypro.bank_Star.entity.StatisticsRule;
import org.skypro.bank_Star.model.Stats;
import org.skypro.bank_Star.service.StatisticsRuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rule")
public class StatisticsRuleController {

private StatisticsRuleService statisticsRuleService;

    public StatisticsRuleController(StatisticsRuleService statisticsRuleService) {
        this.statisticsRuleService = statisticsRuleService;
    }

    @GetMapping("/stats")
    public Stats getAllStatisticsRule() {

        List<StatisticsRule> stats = statisticsRuleService.getAll();
        return new Stats(stats);
    }


}
