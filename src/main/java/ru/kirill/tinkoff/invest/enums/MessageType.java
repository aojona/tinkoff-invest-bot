package ru.kirill.tinkoff.invest.enums;

import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kirill.tinkoff.invest.util.KeyboardUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MessageType {
    START {
        @Override
        public String getReply(MessageSource messageSource, Message message) {
            Object[] arguments = {message.getChat().getFirstName(), message.getChat().getLastName()};
            return messageSource.getMessage(message.getText(), arguments, Locale.US);
        }

        @Override
        public InlineKeyboardMarkup getKeyboardMarkup() {
            return KeyboardUtil.getStartKeyboard();
        }
    },
    CURRENCY {
        @Override
        public InlineKeyboardMarkup getKeyboardMarkup() {
            return KeyboardUtil.getNominalKeyboard();
        }
    },
    GOODBYE,
    SUBSCRIBE {
        @Override
        public String getMessage(MessageSource messageSource, CallbackQuery callbackQuery) {
            String[] queries = callbackQuery.getData().split("\\.");
            Object[] arguments = {
                    callbackQuery.getMessage().getChat().getFirstName(),
                    CurrencyType.getByFigi(queries[1]).getName()
            };
            return messageSource.getMessage("/" + queries[0], arguments, Locale.US);
        }
    };

    private static final Map<String, MessageType> MESSAGE_TYPES;
    static {
        MESSAGE_TYPES = Arrays
                .stream(MessageType.values())
                .collect(Collectors.toMap(
                        MessageType::name,
                        Function.identity()
                ));
    }

    public String getReply(MessageSource messageSource, Message message) {
        return messageSource.getMessage(message.getText(), null, Locale.US);
    }

    public InlineKeyboardMarkup getKeyboardMarkup() {
        return null;
    }

    public static Optional<MessageType> getMessageType(String name) {
        return name.contains(".")
                ? Optional.ofNullable(MESSAGE_TYPES.get(name.toUpperCase().split("\\.")[0]))
                : Optional.ofNullable(MESSAGE_TYPES.get(name.toUpperCase()));
    }

    public String getMessage(MessageSource messageSource, CallbackQuery callbackQuery) {
        return messageSource.getMessage("/" + callbackQuery.getData(), null, Locale.US);
    }
}
