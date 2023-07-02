package ru.kirill.tinkoff.invest.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kirill.tinkoff.invest.entity.Currency;
import ru.kirill.tinkoff.invest.mapper.CurrencyMapper;
import ru.tinkoff.piapi.contract.v1.*;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.MarketDataService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TinkoffClient {

    private static final int ORDER_BOOK_DEPTH = 1;
    private final MarketDataService marketDataService;
    private final InstrumentsService instrumentsService;

    public List<Currency> getCurrencies() {
        return instrumentsService
                .getAllCurrenciesSync()
                .stream()
                .filter(currency -> currency.getFigi().startsWith("BBG"))
                .map(CurrencyMapper::toEntity)
                .toList();
    }

    public Currency getPrice(Currency currency) {
        Quotation quotation = marketDataService
                .getOrderBookSync(currency.getFigi(), ORDER_BOOK_DEPTH)
                .getLastPrice();
        BigDecimal lastPrice = quotionToBigDecimal(quotation, currency.getNominal());
        currency.setPrice(lastPrice);
        return currency;
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
