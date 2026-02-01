package org.skypro.bank_Star.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dynamic_rule")
public class DynamicRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String query;


    @Convert(converter = RuleArgumentsConverter.class)
    private List<String> arguments;


    private Boolean negate;

    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    @JsonBackReference
    private DynamicRecommendation dynamicRecommendation;

    @OneToOne(mappedBy = "dynamicRule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private StatisticsRule statisticsRule;



    public DynamicRule(Long id, String query, List<String> arguments, Boolean negate, DynamicRecommendation dynamicRecommendation) {
        this.id = id;
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
        this.dynamicRecommendation = dynamicRecommendation;
    }

    public DynamicRule() {
    }

    public Long getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public Boolean getNegate() {
        return negate;
    }

    public DynamicRecommendation getDynamicRecommendation() {
        return dynamicRecommendation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void setNegate(Boolean negate) {
        this.negate = negate;
    }

    public void setDynamicRecommendation(DynamicRecommendation dynamicRecommendation) {
        this.dynamicRecommendation = dynamicRecommendation;
    }

    public StatisticsRule getStatisticsRule() {
        return statisticsRule;
    }

    public void setStatisticsRule(StatisticsRule statisticsRule) {
        this.statisticsRule = statisticsRule;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRule that = (DynamicRule) o;
        return Objects.equals(id, that.id) && Objects.equals(query, that.query) && Objects.equals(arguments, that.arguments) && Objects.equals(negate, that.negate) && Objects.equals(dynamicRecommendation, that.dynamicRecommendation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, arguments, negate, dynamicRecommendation);
    }

    @Override
    public String toString() {
        return "DynamicRule{" +
                "id=" + id +
                ", query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                ", dynamicRecommendation=" + dynamicRecommendation +
                '}';
    }
}
