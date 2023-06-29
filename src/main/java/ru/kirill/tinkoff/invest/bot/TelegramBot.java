package ru.kirill.tinkoff.invest.bot;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Getter
@Builder
public class TelegramBot extends TelegramWebhookBot {

    private final String botUsername;
    private final String botToken;
    private final String botPath;
    private final UpdateHandler updateHandler;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            return updateHandler.handleCallbackQuery(update.getCallbackQuery());
        }
        return updateHandler.handleMessage(update.getMessage());
    }
}
