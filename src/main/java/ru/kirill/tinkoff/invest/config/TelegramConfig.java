package ru.kirill.tinkoff.invest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.kirill.tinkoff.invest.bot.TelegramBot;
import ru.kirill.tinkoff.invest.bot.UpdateHandler;
import ru.kirill.tinkoff.invest.config.properties.TelegramProperties;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TelegramConfig {

    private final TelegramProperties properties;

    @Bean
    public SetWebhook setWebhook() {
        return SetWebhook.builder()
                .url(properties.webHookPath())
                .build();
    }

    @Bean
    public TelegramBot telegramBot(UpdateHandler updateHandler) {
        return TelegramBot.builder()
                .botUsername(properties.name())
                .botToken(properties.token())
                .botPath(properties.webHookPath())
                .updateHandler(updateHandler)
                .build();
    }
}
