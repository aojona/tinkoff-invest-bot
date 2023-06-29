package ru.kirill.tinkoff.invest.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kirill.tinkoff.invest.enums.CurrencyType;
import ru.tinkoff.piapi.contract.v1.*;
import ru.tinkoff.piapi.core.MarketDataService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
@RequiredArgsConstructor
public class TinkoffClient {

    private static final int ORDER_BOOK_DEPTH = 1;
    private final MarketDataService marketDataService;

    public CurrencyResponse getPrice(String instrumentId) {
        GetOrderBookResponse orderBook = marketDataService.getOrderBookSync(instrumentId, ORDER_BOOK_DEPTH);
        Quotation lastPrice = orderBook.getLastPrice();
        Quotation closePrice = orderBook.getClosePrice();
        CurrencyType currencyType = CurrencyType.getByFigi(instrumentId);
        return CurrencyResponse
                .builder()
                .name(currencyType.getName())
                .closePrice(quotionToBigDecimal(closePrice, currencyType.getNominal()))
                .lastPrice(quotionToBigDecimal(lastPrice, currencyType.getNominal()))
                .build();
    }

    private static BigDecimal quotionToBigDecimal(Quotation quotation, long nominal) {
        return quotation.getUnits() == 0 && quotation.getNano() == 0
                ? BigDecimal.ZERO
                : BigDecimal
                .valueOf(quotation.getUnits())
                .add(BigDecimal.valueOf(quotation.getNano(), 9))
                .divide(BigDecimal.valueOf(nominal), RoundingMode.HALF_EVEN);
    }
}
