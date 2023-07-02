package ru.kirill.tinkoff.invest.mapper;

import lombok.experimental.UtilityClass;
import ru.kirill.tinkoff.invest.entity.Currency;

@UtilityClass
public class CurrencyMapper {
    public static Currency toEntity(ru.tinkoff.piapi.contract.v1.Currency currency) {
        return Currency
                .builder()
                .figi(currency.getFigi())
                .name(currency.getName())
                .abbreviation(currency.getNominal().getCurrency().toUpperCase())
                .nominal(currency.getNominal().getUnits())
                .build();
    }
}