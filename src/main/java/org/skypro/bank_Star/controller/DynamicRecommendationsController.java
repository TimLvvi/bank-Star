package org.skypro.bank_Star.controller;

import org.skypro.bank_Star.entity.DynamicRecommendation;
import org.skypro.bank_Star.service.DynamicRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для работы с рекомендациями и правилами.
 * Основной endpoint для менеджера банка.
 */
@RestController
@RequestMapping("dynamic_recommendations")
public class DynamicRecommendationsController {

    private DynamicRecommendationService dynamicRecommendationService;

    public DynamicRecommendationsController(DynamicRecommendationService dynamicRecommendationService) {
        this.dynamicRecommendationService = dynamicRecommendationService;
    }

    /**
     * Создает новую динамическую рекомендацию.
     */
    @PostMapping
    public DynamicRecommendation createDynamicRecommendation(@RequestBody DynamicRecommendation dynamicRecommendation) {
        return dynamicRecommendationService.createDynamicRecommendation(dynamicRecommendation);
    }

    /**
     * Удаляет динамическую рекомендацию по идентификатору.
     */
    @DeleteMapping("{id}")
    public ResponseEntity deleteDynamicRecommendation(@PathVariable long id) {
        dynamicRecommendationService.deleteDynamicRecommendation(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Возвращает список всех динамических рекомендаций.
     */
    @GetMapping
    public List<DynamicRecommendation> getALLDynamicRecommendation() {
        return dynamicRecommendationService.getAll();
    }
}
