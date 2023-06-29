package ru.kirill.tinkoff.invest.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kirill.tinkoff.invest.client.CurrencyResponse;
import ru.kirill.tinkoff.invest.client.TinkoffClient;
import ru.kirill.tinkoff.invest.enums.MessageType;
import ru.kirill.tinkoff.invest.util.KeyboardUtil;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final TinkoffClient tinkoffClient;
    private final MessageSource messageSource;

    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        Optional<MessageType> messageType = MessageType.getMessageType(callbackQuery.getData());
        return messageType.isPresent()
                ? createReplyByMessageType(callbackQuery, messageType.get())
                : showCurrencyPrice(callbackQuery);
    }

    public BotApiMethod<?> handleMessage(Message message) {
        String command = message.getText().replace("/", "").toUpperCase();
        Optional<MessageType> messageType = MessageType.getMessageType(command);
        return messageType
                .map(type -> createReply(
                        message,
                        type.getReply(messageSource, message),
                        type.getKeyboardMarkup()
                ))
                .orElse(null);
    }

    private SendMessage createReplyByMessageType(CallbackQuery callbackQuery, MessageType messageType) {
        return createReply(
                callbackQuery.getMessage(),
                messageType.getMessage(messageSource, callbackQuery),
                messageType.getKeyboardMarkup());
    }

    private SendMessage showCurrencyPrice(CallbackQuery callbackQuery) {
        String figi = callbackQuery.getData();
        CurrencyResponse price = tinkoffClient.getPrice(figi);
        Object[] arguments = {price.getName(), price.getLastPrice(), price.getClosePrice()};
        return createReply(
                callbackQuery.getMessage(),
                messageSource.getMessage("reply.currency", arguments, Locale.US),
                KeyboardUtil.getSubscribeMarkup(figi)
        );
    }

    private SendMessage createReply(Message message, String text, InlineKeyboardMarkup keyboardMarkup) {
        return SendMessage
                .builder()
                .chatId(message.getChatId())
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();
    }
}
