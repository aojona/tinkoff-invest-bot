package ru.kirill.tinkoff.invest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kirill.tinkoff.invest.bot.TelegramBot;
import ru.kirill.tinkoff.invest.bot.UpdateHandler;
import ru.kirill.tinkoff.invest.config.properties.TelegramProperties;
import ru.kirill.tinkoff.invest.util.KeyboardUtil;

import java.util.Collections;

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
    public TelegramBot telegramBot(UpdateHandler updateHandler) {
        return TelegramBot.builder()
                .botUsername(properties.name())
                .botToken(properties.token())
                .botPath(properties.webHookPath())
                .updateHandler(updateHandler)
                .build();
    }

    @Bean
    public InlineKeyboardMarkup nominalKeyboard() {
        return KeyboardUtil.getNominalKeyboard();
    }

    @Bean
    public InlineKeyboardMarkup startKeyboard() {
        return new InlineKeyboardMarkup(Collections.singletonList(Collections.singletonList(InlineKeyboardButton
                .builder()
                .text("Да")
                .callbackData("buttonYes")
                .build())));
    }
}
