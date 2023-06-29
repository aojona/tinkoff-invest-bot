package ru.kirill.tinkoff.invest.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kirill.tinkoff.invest.client.TinkoffClient;
import ru.kirill.tinkoff.invest.enums.MessageType;
import ru.kirill.tinkoff.invest.enums.Nominal;
import ru.tinkoff.piapi.contract.v1.GetOrderBookResponse;
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
                ? showCurrencies(callbackQuery, messageType.get())
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

    private SendMessage showCurrencies(CallbackQuery callbackQuery, MessageType messageType) {
        return createReply(
                callbackQuery.getMessage(),
                messageSource.getMessage("/" + callbackQuery.getData(), null, Locale.US),
                messageType.getKeyboardMarkup());
    }

    private SendMessage showCurrencyPrice(CallbackQuery callbackQuery) {
        String figi = callbackQuery.getData();
        GetOrderBookResponse price = tinkoffClient.getPrice(figi);
        return createReply(
                callbackQuery.getMessage(),
                Nominal.getByFigi(figi) + "\n" + price.toString(),
                null
        );
    }

    private SendMessage createReply(Message message, String type, InlineKeyboardMarkup keyboardMarkup) {
        return SendMessage
                .builder()
                .chatId(message.getChatId())
                .text(type)
                .replyMarkup(keyboardMarkup)
                .build();
    }
}
