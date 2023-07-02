package ru.kirill.tinkoff.invest.bot;

import lombok.Builder;
import lombok.Getter;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

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
        if (update.hasMessage()) {
            return updateHandler.handleMessage(update.getMessage());
        }
        return null;
    }
}
