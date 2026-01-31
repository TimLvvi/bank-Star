package org.skypro.bank_Star.controller;

import org.skypro.bank_Star.repository.RecommendationsRepository;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("management")
public class ManagementController {
    private RecommendationsRepository recommendationsRepository;
    private final BuildProperties buildProperties;

    public ManagementController(BuildProperties buildProperties, RecommendationsRepository recommendationsRepository) {
        this.buildProperties = buildProperties;
        this.recommendationsRepository = recommendationsRepository;
    }

    @PostMapping("/clear-caches")
    public ResponseEntity clearCache() {
        recommendationsRepository.clearCache();
        return ResponseEntity.ok().build();

    }

    @GetMapping("/info")
    public String getInfo() {
        String result = "name: " + buildProperties.getName()+"\nversion: " + buildProperties.getVersion();
        return  result;
    }

}
