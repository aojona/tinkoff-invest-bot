package ru.kirill.tinkoff.invest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.kirill.tinkoff.invest.bot.TelegramBot;
import ru.kirill.tinkoff.invest.config.properties.TelegramProperties;

@Configuration
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
    public TelegramBot telegramBot() {
        return TelegramBot.builder()
                .botUsername(properties.name())
                .botToken(properties.token())
                .botPath(properties.webHookPath())
                .build();
    }
}
