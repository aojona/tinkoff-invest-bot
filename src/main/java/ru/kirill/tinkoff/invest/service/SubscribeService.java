package ru.kirill.tinkoff.invest.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kirill.tinkoff.invest.bot.TelegramBot;
import ru.kirill.tinkoff.invest.entity.Currency;
import ru.kirill.tinkoff.invest.entity.Subscriber;
import java.util.Locale;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class SubscribeService {

    private static final String CURRENCY_REPLY = "currency.reply";

    private final DataService dataService;
    private final MessageSource messageSource;
    private final TelegramBot bot;

    @Scheduled(fixedRateString = "${schedule.fixed-rate.update-currencies}")
    public void updateCurrencies() {
        dataService
                .getCurrencies()
                .stream()
                .map(Currency::getFigi)
                .forEach(dataService::updateCurrencyPrice);
    }

    @Scheduled(fixedRateString = "${schedule.fixed-rate.send-prices}")
    public void sendPricesToSubscribers()  {
        dataService
                .getSubscribers()
                .forEach(this::sendPricesToSubscriber);
    }

    private void sendPricesToSubscriber(Subscriber subscriber) {
    subscriber
            .getCurrencies()
            .forEach(currency -> {
                log.info("sub: {}, currency: {}", subscriber.getUsername(), currency.getName());
                sendPriceToSubscriber(subscriber, currency);
            });
    }

    @SneakyThrows
    private void sendPriceToSubscriber(Subscriber subscriber, Currency currency)  {
        Object[] arguments = {currency.getName(), currency.getPrice()};
        String message = messageSource.getMessage(CURRENCY_REPLY, arguments, Locale.US);
        SendMessage reply = createReply(subscriber.getChatId(), message);
        bot.execute(reply);
    }

    private SendMessage createReply(long chatId, String text) {
        return SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
}
