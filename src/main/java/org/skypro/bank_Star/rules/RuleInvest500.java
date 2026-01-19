package org.skypro.bank_Star.rules;


import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RuleInvest500 implements Rule {
    private RecommendationsRepository recommendationsRepository;

    public RuleInvest500(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Recommendation getRecommendation(UUID userId) {
        boolean hasDebit = recommendationsRepository.hasProductType(userId, "DEBIT");
        boolean hasInvest = recommendationsRepository.hasProductType(userId, "INVEST");
        int savingDeposits = recommendationsRepository.getTotalDepositsByProductType(userId, "SAVING");

        if (hasDebit && !hasInvest && savingDeposits > 1000) {
            return new Recommendation(
                    "Invest 500",
                    UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                    "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"
            );
        }
        return null;


    }
}
