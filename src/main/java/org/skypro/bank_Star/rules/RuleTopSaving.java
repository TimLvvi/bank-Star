package org.skypro.bank_Star.rules;


import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RuleTopSaving implements Rule {
    private RecommendationsRepository recommendationsRepository;

    public RuleTopSaving(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Recommendation getRecommendation(UUID userId) {
        boolean userOf = recommendationsRepository.userOf(userId, "DEBIT", false);
        boolean transactionSumCompareDepositWithdraw = recommendationsRepository.transactionSumCompareDepositWithdraw(userId, "DEBIT", ">", false);
        boolean transactionSumCompare = recommendationsRepository.transactionSumCompare(userId, "SAVING", "DEPOSIT", ">=", 50000, false);
        boolean transactionSumCompare1 = recommendationsRepository.transactionSumCompare(userId, "DEBIT", "DEPOSIT", ">=", 50000, false);

        boolean transactionSumCompare3 = transactionSumCompare || transactionSumCompare1;


        if (userOf && transactionSumCompare3 && transactionSumCompareDepositWithdraw) {
            return new Recommendation(
                    "Top Saving",
                    UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                    "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем! Преимущества «Копилки»: Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет. Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости. Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг. Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"
            );
        }
        return null;
    }
}

