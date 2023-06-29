package ru.kirill.tinkoff.invest.enums;

import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kirill.tinkoff.invest.util.KeyboardUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MessageType {
    START() {
        @Override
        public String getReply(MessageSource messageSource, Message message) {
            Object[] arguments = {message.getChat().getFirstName(), message.getChat().getLastName()};
            return messageSource.getMessage(message.getText(), arguments, Locale.getDefault());
        }

        @Override
        public InlineKeyboardMarkup getKeyboardMarkup() {
            return KeyboardUtil.getStartKeyboard();
        }
    },
    CURRENCY() {
        @Override
        public InlineKeyboardMarkup getKeyboardMarkup() {
            return KeyboardUtil.getNominalKeyboard();
        }
    },
    GOODBYE();

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
        return messageSource.getMessage(message.getText(), null, Locale.getDefault());
    }

    public InlineKeyboardMarkup getKeyboardMarkup() {
        return null;
    }

    public static Optional<MessageType> getMessageType(String name) {
        return Optional.ofNullable(MESSAGE_TYPES.get(name.toUpperCase()));
    }
}
