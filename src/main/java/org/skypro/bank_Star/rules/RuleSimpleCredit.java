package org.skypro.bank_Star.rules;


import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RuleSimpleCredit implements Rule {
    private RecommendationsRepository recommendationsRepository;

    public RuleSimpleCredit(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Recommendation getRecommendation(UUID userId) {
        boolean userOf = recommendationsRepository.userOf(userId, "CREDIT", true);
        boolean transactionSumCompareDepositWithdraw = recommendationsRepository.transactionSumCompareDepositWithdraw(userId, "DEBIT", ">", false);
        boolean transactionSumCompare = recommendationsRepository.transactionSumCompare(userId, "DEBIT", "WITHDRAW", ">", 100000, false);


        if (userOf && transactionSumCompareDepositWithdraw && transactionSumCompare) {
            return new Recommendation(
                    "Простой кредит",
                    UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    "Откройте мир выгодных кредитов с нами! Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту. Почему выбирают нас: Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов. Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" + "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое. Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"
            );
        }
        return null;
    }
}
