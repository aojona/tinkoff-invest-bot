package ru.kirill.tinkoff.invest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kirill.tinkoff.invest.bot.TelegramBot;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebController {

    private final TelegramBot telegramBot;

    @PostMapping("/")
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
