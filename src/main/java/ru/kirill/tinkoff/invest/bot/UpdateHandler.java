package ru.kirill.tinkoff.invest.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kirill.tinkoff.invest.entity.Currency;
import ru.kirill.tinkoff.invest.entity.Subscriber;
import ru.kirill.tinkoff.invest.mapper.SubscriberMapper;
import ru.kirill.tinkoff.invest.service.DataService;
import ru.kirill.tinkoff.invest.util.KeyboardUtil;
import java.util.List;
import java.util.Locale;

import static ru.kirill.tinkoff.invest.util.KeyboardUtil.getCurrencyKeyboard;
import static ru.kirill.tinkoff.invest.util.KeyboardUtil.getStartKeyboard;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private static final Locale LOCALE      = Locale.US;
    private static final String M_START     = "/start";
    private static final String START       = "start";
    private static final String CURRENCY    = "currency";
    private static final String CURRENCIES  = "currencies";
    private static final String END         = "end";
    private static final String REPLY       = ".reply";
    private static final String SUBSCRIBE   = "subscribe";

    private final DataService dataService;
    private final MessageSource messageSource;

    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChat().getId();
        String source = callbackQuery.getData();
        return switch (source.split("\\.")[0]) {
            case START              -> handleStartCallback(chatId, source);
            case CURRENCY           -> handleCurrencyCallback(chatId, source);
            case SUBSCRIBE          -> handleSubscribeCallback(chatId, source);
            default                 -> null;
        };
    }

    public BotApiMethod<?> handleMessage(Message message) {
        String data = message.getText();
        return switch(data) {
            case M_START        -> handleStartMessage(message.getChat());
            default             -> null;
        };
    }

    private BotApiMethod<?> handleStartCallback(Long chatId, String source) {
        return switch (source.split("\\.")[1]) {
            case CURRENCIES     -> handleStartCurrenciesCallback(chatId, source);
            case END            -> handleStartEndCallback(chatId, source);
            default             -> null;
        };
    }

    private BotApiMethod<?> handleCurrencyCallback(Long chatId, String source) {
        Currency currency = dataService.getCurrencyBiFigi(source.split("\\.")[1]);
        Object[] arguments = {currency.getName(),currency.getPrice()};
        String message = messageSource.getMessage(CURRENCY + REPLY, arguments, LOCALE);
        InlineKeyboardMarkup subscribeMarkup = KeyboardUtil.getSubscribeMarkup(currency.getFigi());
        return createReply(chatId, message, subscribeMarkup);
    }

    private BotApiMethod<?> handleStartEndCallback(Long chatId, String source) {
        String message = messageSource.getMessage(source + REPLY, null, LOCALE);
        return createReply(chatId, message, null);
    }

    private BotApiMethod<?> handleStartCurrenciesCallback(long chatId, String source) {
        String message = messageSource.getMessage(source + REPLY, null, LOCALE);
        List<Currency> currencies = dataService.getCurrencies();
        InlineKeyboardMarkup currencyKeyboard = getCurrencyKeyboard(currencies);
        return createReply(chatId, message, currencyKeyboard);
    }

    private BotApiMethod<?> handleSubscribeCallback(Long chatId, String source) {
        String figi = source.split("\\.")[1];
        Subscriber subscriber = dataService.addCurrencyToSubscriber(chatId, figi);
        Currency currency = dataService.getCurrencyBiFigi(figi);
        Object[] arguments = {subscriber.getFirstname(), currency.getName()};
        String message = messageSource.getMessage(SUBSCRIBE + REPLY, arguments, LOCALE);
        return createReply(chatId, message, null);
    }

    private BotApiMethod<?> handleStartMessage(Chat chat) {
        if (dataService.findSubscriber(chat.getId()).isEmpty()) {
            dataService.addSubscriber(SubscriberMapper.toEntity(chat));
        }
        Object[] arguments = {chat.getFirstName(), chat.getLastName()};
        String message = messageSource.getMessage(M_START, arguments, LOCALE);
        InlineKeyboardMarkup startKeyboard = getStartKeyboard();
        return createReply(chat.getId(), message, startKeyboard);
    }

    private SendMessage createReply(long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        return SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();
    }
}
