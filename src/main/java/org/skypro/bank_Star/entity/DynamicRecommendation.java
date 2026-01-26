package org.skypro.bank_Star.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Entity
@Table(name = "dynamic_recommendation")
public class DynamicRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_text")
    private String productText;

    @OneToMany(mappedBy = "dynamicRecommendation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DynamicRule> dynamicRule;

    public DynamicRecommendation() {
    }

    public DynamicRecommendation(Long id, String productName, UUID productId, String productText, List<DynamicRule> dynamicRule) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.dynamicRule = dynamicRule;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductText() {
        return productText;
    }

    public List<DynamicRule> getDynamicRule() {
        return dynamicRule;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public void setDynamicRule(List<DynamicRule> dynamicRule) {
        this.dynamicRule = dynamicRule;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRecommendation that = (DynamicRecommendation) o;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(productId, that.productId) && Objects.equals(productText, that.productText) && Objects.equals(dynamicRule, that.dynamicRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, dynamicRule);
    }

    @Override
    public String toString() {
        return "DynamicRecommendation{" +
                "id=" + id +
                ", name='" + productName + '\'' +
                ", recommendationId=" + productId +
                ", text='" + productText + '\'' +
                ", dynamicRule=" + dynamicRule +
                '}';
    }
}
