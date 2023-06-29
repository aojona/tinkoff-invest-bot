package ru.kirill.tinkoff.invest.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum CurrencyType {

    AMD("Армянский драм",       "BBG0013J7V24", 100),
    BYN("Белорусский рубль",    "BBG00D87WQY7", 1),
    HKD("Гонконгский доллар",   "BBG0013HSW87", 1),
    AED("Дирхам ОАЭ",           "BBG0013HJ924", 1),
    USD("Доллар США",           "BBG0013HGFT4", 1),
    XAU("Золото",               "BBG000VJ5YR4", 1),
    EUR("Евро",                 "BBG0013HJJ31", 1),
    KZT("Казахстанский тенге",  "BBG0013HG026", 100),
    KGS("Киргизский сом",       "BBG0013J7Y00", 100),
    CNY("Китайский юань",       "BBG0013HRTL0", 1),
    XAG("Серебро",              "BBG000VHQTD1", 1),
    TJS("Таджикский сомони",    "BBG0013J11P1", 10),
    TRY("Турецкая лира",        "BBG0013J12N1", 1),
    UZS("Узбекский сум",        "BBG0013HQ310", 10_000),
    GBP("Фунт стерлингов",      "BBG0013HQ5F0", 1),
    CHF("Швейцарский франк",    "BBG0013HQ5K4", 1),
    ZAR("Южноафриканский рэнд", "BBG0013HLF18", 10),
    JPY("Японская иена",        "BBG0013HQ524", 100);

    private static final Map<String, CurrencyType> NOMINAL_MAP;

    static {
        NOMINAL_MAP = Arrays
                .stream(CurrencyType.values())
                .collect(Collectors.toMap(CurrencyType::getFigi, Function.identity()));
    }

    private final String name;
    private final String figi;
    private final long nominal;

    CurrencyType(String name, String figi, long nominal) {
        this.name = name;
        this.figi = figi;
        this.nominal = nominal;
    }

    public static CurrencyType getByFigi(String figi) {
        return NOMINAL_MAP.get(figi);
    }
}
