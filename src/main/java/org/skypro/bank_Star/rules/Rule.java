package org.skypro.bank_Star.rules;


import org.skypro.bank_Star.model.Recommendation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
interface Rule {
    Recommendation getRecommendation(UUID userId);
}
