package org.skypro.bank_Star.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.skypro.bank_Star.model.Recommendation;
import org.skypro.bank_Star.model.RecommendationResponse;
import org.skypro.bank_Star.repository.DynamicRecommendationsRepositiry;
import org.skypro.bank_Star.repository.RecommendationsRepository;
import org.skypro.bank_Star.service.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    @Autowired
    private TelegramBot telegramBot;

    private RecommendationsRepository recommendationsRepository;
    private RecommendationService recommendationService;

    public TelegramBotUpdatesListener(RecommendationsRepository recommendationsRepository, RecommendationService recommendationService) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationService = recommendationService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);


            if (update.message() == null || update.message().text() == null) {
                logger.info("Skipping update - no message or text");
                return;
            }

            String messageText = update.message().text();
            Long chatId = update.message().chat().id();

            if (messageText.equals("/start")) {
                String welcomeMessage = "Добро пожаловать";
                SendMessage message = new SendMessage(chatId, welcomeMessage);
                telegramBot.execute(message);
            }

            if (messageText.startsWith("/recommend")) {
                String[] nameParts = messageText.split(" ");
                if (nameParts.length == 2) {
                    UUID id = recommendationsRepository.findUserId(nameParts[1]);
                    if (id == null) {
                        telegramBot.execute(new SendMessage(chatId, "Пользователь не найден"));
                    } else {
                        String firstName = recommendationsRepository.findUserFirstName(nameParts[1]);
                        String lastName = recommendationsRepository.findUserLasttName(nameParts[1]);

                        RecommendationResponse recommendationResponse = recommendationService.getRecommendation(id);
                        String s = recommendationResponse.getRecommendations().stream()
                                .map(r -> r.getName() + " - " + r.getText())
                                .collect(Collectors.joining("\n"));

                        String s2 = "Здравствуйте " + firstName + " " + lastName + "\n" + "Новые продукты для вас:\n" + s.toString();


                        SendMessage message = new SendMessage(chatId, s2);
                        telegramBot.execute(message);
                    }
                }

                if (nameParts.length != 2) {
                    String incorrect = "Неверно введен запрос. Используйте формат /recommend Имя Фамилия";
                    SendMessage incorrectMessage = new SendMessage(chatId, incorrect);
                    telegramBot.execute(incorrectMessage);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}

