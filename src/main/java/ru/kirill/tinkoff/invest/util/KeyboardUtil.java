package ru.kirill.tinkoff.invest.util;

import lombok.experimental.UtilityClass;
import one.util.streamex.StreamEx;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kirill.tinkoff.invest.entity.Currency;
import java.util.*;

@UtilityClass
public class KeyboardUtil {

    public static InlineKeyboardMarkup getStartKeyboard() {
        return InlineKeyboardMarkup
                .builder()
                .keyboard(getStartButtons())
                .build();
    }

    public static InlineKeyboardMarkup getCurrencyKeyboard(List<Currency> currencies) {
        return InlineKeyboardMarkup
                .builder()
                .keyboard(getCurrencyButtons(currencies))
                .build();
    }

    public static InlineKeyboardMarkup getSubscribeMarkup(String figi) {
        String callback = "subscribe." + figi;
        String message = ResourceBundle
                .getBundle("messages", Locale.US)
                .getString(callback.split("\\.")[0]);
        return InlineKeyboardMarkup
                .builder()
                .keyboard(Optional
                        .of(createButton(message, callback))
                        .map(List::of)
                        .map(Collections::singleton)
                        .get()
                )
                .build();
    }

    private static List<List<InlineKeyboardButton>> getCurrencyButtons(List<Currency> currencies) {
        return StreamEx
                .ofSubLists(getOneDimensionalCurrencyButtons(currencies), 2)
                .toList();
    }

    private static List<InlineKeyboardButton> getOneDimensionalCurrencyButtons(List<Currency> currencies) {
        return currencies
                .stream()
                .map(currency -> createButton(currency.getName(), "currency." + currency.getFigi()))
                .toList();
    }

    private static List<List<InlineKeyboardButton>> getStartButtons() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.US);
        return List.of(bundle
                .keySet()
                .stream()
                .filter(key -> key.startsWith("start") && !key.endsWith("reply"))
                .sorted(Comparator.reverseOrder())
                .map(key -> createButton(bundle.getString(key), key))
                .toList());
    }

    private static InlineKeyboardButton createButton(String text, String callback) {
        return InlineKeyboardButton
                .builder()
                .text(text)
                .callbackData(callback)
                .build();
    }
}
