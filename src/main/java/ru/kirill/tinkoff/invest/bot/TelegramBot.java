package ru.kirill.tinkoff.invest.bot;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Getter
@Builder
public class TelegramBot extends TelegramWebhookBot {

    private String botUsername;
    private String botToken;
    private String botPath;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        return new SendMessage(String.valueOf(chatId), "Hi");
    }
}
