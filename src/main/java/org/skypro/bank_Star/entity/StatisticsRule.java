package org.skypro.bank_Star.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name= "statistics_rule")
public class StatisticsRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_triggers")
    private Long count;


    @OneToOne
    @JoinColumn(name = "dynamic_rule_id")
    @JsonBackReference
    private DynamicRule dynamicRule;

    public StatisticsRule(Long id, Long count, DynamicRule dynamicRule) {
        this.id = id;
        this.count = count;
        this.dynamicRule = dynamicRule;
    }

    public StatisticsRule() {
    }

    public Long getId() {
        return id;
    }

    public Long getCount() {
        return count;
    }

    public DynamicRule getDynamicRule() {
        return dynamicRule;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setDynamicRule(DynamicRule dynamicRule) {
        this.dynamicRule = dynamicRule;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsRule that = (StatisticsRule) o;
        return Objects.equals(id, that.id) && Objects.equals(count, that.count) && Objects.equals(dynamicRule, that.dynamicRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, dynamicRule);
    }

    @Override
    public String toString() {
        return "StatisticsRule{" +
                "id=" + id +
                ", count=" + count +
                ", dynamicRule=" + dynamicRule +
                '}';
    }
}
