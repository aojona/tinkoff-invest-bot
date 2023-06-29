package ru.kirill.tinkoff.invest.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Nominal {

    AMD("Армянский драм",       "BBG0013J7V24"),
    BYN("Белорусский рубль",    "BBG00D87WQY7"),
    HKD("Гонконгский доллар",   "BBG0013HSW87"),
    AED("Дирхам ОАЭ",           "BBG0013HJ924"),
    USD("Доллар США",           "BBG0013HGFT4"),
    XAU("Золото",               "BBG000VJ5YR4"),
    EUR("Евро",                 "BBG0013HJJ31"),
    KZT("Казахстанский тенге",  "BBG0013HG026"),
    KGS("Киргизский сом",       "BBG0013J7Y00"),
    CNY("Китайский юань",       "BBG0013HRTL0"),
    RUB("Российский рубль",     "RUB000UTSTOM"),
    XAG("Серебро",              "BBG000VHQTD1"),
    TJS("Таджикский сомони",    "BBG0013J11P1"),
    TRY("Турецкая лира",        "BBG0013J12N1"),
    UZS("Узбекский сум",        "BBG0013HQ310"),
    GBP("Фунт стерлингов",      "BBG0013HQ5F0"),
    CHF("Швейцарский франк",    "BBG0013HQ5K4"),
    ZAR("Южноафриканский рэнд", "BBG0013HLF18"),
    JPY("Японская иена",        "BBG0013HQ524");

    private static final Map<String, Nominal> NOMINAL_MAP;

    static {
        NOMINAL_MAP = Arrays
                .stream(Nominal.values())
                .collect(Collectors.toMap(Nominal::getFigi, Function.identity()));
    }

    private final String name;
    private final String figi;

    Nominal(String name, String figi) {
        this.name = name;
        this.figi = figi;
    }

    public static Nominal getByFigi(String figi) {
        return NOMINAL_MAP.get(figi);
    }
}
