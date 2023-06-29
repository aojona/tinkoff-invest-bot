package ru.kirill.tinkoff.invest.util;

import lombok.experimental.UtilityClass;
import one.util.streamex.StreamEx;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kirill.tinkoff.invest.enums.Nominal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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

    private static List<List<InlineKeyboardButton>> getNominalButtons() {
        return StreamEx
                .ofSubLists(getOneDimensionalNominalButtons(), 2)
                .toList();
    }

    private static List<InlineKeyboardButton> getOneDimensionalNominalButtons() {
        return Arrays
                .stream(Nominal.values())
                .map(nominal -> createButton(nominal.getName(), nominal.getFigi()))
                .toList();
    }

    private static List<List<InlineKeyboardButton>> getStartButtons() {
        ResourceBundle bundle = ResourceBundle.getBundle("start", Locale.getDefault());
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
