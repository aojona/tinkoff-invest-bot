package ru.kirill.tinkoff.invest.util;

import lombok.experimental.UtilityClass;
import one.util.streamex.StreamEx;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kirill.tinkoff.invest.enums.CurrencyType;

import java.util.*;

@UtilityClass
public class KeyboardUtil {

    public static InlineKeyboardMarkup getStartKeyboard() {
        return InlineKeyboardMarkup
                .builder()
                .keyboard(getStartButtons())
                .build();
    }

    public static InlineKeyboardMarkup getNominalKeyboard() {
        return InlineKeyboardMarkup
                .builder()
                .keyboard(getNominalButtons())
                .build();
    }

    public static InlineKeyboardMarkup getSubscribeMarkup(String figi) {
        String key = "subscribe." + figi;
        String message = ResourceBundle
                .getBundle("messages", Locale.US)
                .getString(key.split("\\.")[0]);
        return InlineKeyboardMarkup
                .builder()
                .keyboard(Optional
                        .of(createButton(message, key))
                        .map(List::of)
                        .map(Collections::singleton)
                        .get()
                )
                .build();
    }

    private static List<List<InlineKeyboardButton>> getNominalButtons() {
        return StreamEx
                .ofSubLists(getOneDimensionalNominalButtons(), 2)
                .toList();
    }

    private static List<InlineKeyboardButton> getOneDimensionalNominalButtons() {
        return Arrays
                .stream(CurrencyType.values())
                .map(nominal -> createButton(nominal.getName(), nominal.getFigi()))
                .toList();
    }

    private static List<List<InlineKeyboardButton>> getStartButtons() {
        ResourceBundle bundle = ResourceBundle.getBundle("start", Locale.US);
        return List.of(bundle
                .keySet()
                .stream()
                .map(key -> createButton(bundle.getString(key), key))
                .toList());
    }

    private static InlineKeyboardButton createButton(String text, String callbackData) {
        return InlineKeyboardButton
                .builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }
}
