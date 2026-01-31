package org.skypro.bank_Star.model;

import org.skypro.bank_Star.entity.StatisticsRule;

import java.util.List;
import java.util.Objects;

public class Stats {
    private List<StatisticsRule> stats;

    public Stats(List<StatisticsRule> stats) {
        this.stats = stats;
    }

    public List<StatisticsRule> getStats() {
        return stats;
    }

    public void setStats(List<StatisticsRule> stats) {
        this.stats = stats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats1 = (Stats) o;
        return Objects.equals(stats, stats1.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stats);
    }

    @Override
    public String toString() {
        return "Stats{" +
                "stats=" + stats +
                '}';
    }
}
