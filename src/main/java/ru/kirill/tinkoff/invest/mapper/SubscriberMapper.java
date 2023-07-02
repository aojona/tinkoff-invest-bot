package ru.kirill.tinkoff.invest.mapper;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.kirill.tinkoff.invest.entity.Subscriber;

@UtilityClass
public class SubscriberMapper {
    public static Subscriber toEntity(Chat chat) {
        return Subscriber
                .builder()
                .chatId(chat.getId())
                .username(chat.getUserName())
                .firstname(chat.getFirstName())
                .lastname(chat.getLastName())
                .build();
    }
}
