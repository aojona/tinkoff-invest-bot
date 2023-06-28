package ru.kirill.tinkoff.invest.util;

import one.util.streamex.StreamEx;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kirill.tinkoff.invest.enums.Nominal;

import java.util.Arrays;
import java.util.List;

public class KeyboardUtil {

    public static InlineKeyboardMarkup getNominalKeyboard() {
        return InlineKeyboardMarkup
                .builder()
                .keyboard(getNominalButtons())
                .build();
    }

    public static List<List<InlineKeyboardButton>> getNominalButtons() {
        return StreamEx
                .ofSubLists(getOneDimensionalNominalButtons(), 2)
                .toList();
    }

    private static List<InlineKeyboardButton> getOneDimensionalNominalButtons() {
        return Arrays
                .stream(Nominal.values())
                .map(KeyboardUtil::getNominalButton)
                .toList();
    }

    public static InlineKeyboardButton getNominalButton(Nominal nominal) {
        return InlineKeyboardButton
                .builder()
                .text(nominal.getName())
                .callbackData(nominal.getFigi())
                .build();
    }
}
