package ru.kirill.tinkoff.invest.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kirill.tinkoff.invest.client.TinkoffClient;
import ru.kirill.tinkoff.invest.enums.Nominal;
import ru.tinkoff.piapi.contract.v1.GetOrderBookResponse;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final TinkoffClient tinkoffClient;
    private final InlineKeyboardMarkup nominalKeyboard;
    public BotApiMethod<?> handleCallbackQuerry(CallbackQuery callbackQuery) {

        String figi = callbackQuery.getData();
        GetOrderBookResponse price = tinkoffClient.getPrice(figi);
        return SendMessage
                .builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .text(Nominal.getByFigi(figi) + "\n" + price.toString())
                .replyMarkup(nominalKeyboard)
                .build();
    }
}
