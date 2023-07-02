package ru.kirill.tinkoff.invest.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class CurrencyResponse {

    private String name;
    private BigDecimal lastPrice;
    private BigDecimal closePrice;
}
