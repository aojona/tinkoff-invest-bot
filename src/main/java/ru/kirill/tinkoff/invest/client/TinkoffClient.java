package ru.kirill.tinkoff.invest.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.*;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.MarketDataService;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TinkoffClient {

    private static final int ORDER_BOOK_DEPTH = 1;

    private final InstrumentsService instrumentsService;
    private final MarketDataService marketDataService;

    public List<Currency> getCurrencies() {
        return instrumentsService.getCurrenciesSync(InstrumentStatus.INSTRUMENT_STATUS_ALL);
    }

    public Currency getCurrency(String figi) {
        return instrumentsService.getCurrencyByFigiSync(figi);
    }

    public GetOrderBookResponse getPrice(String instrumentId) {
        return marketDataService.getOrderBookSync(instrumentId, ORDER_BOOK_DEPTH);
    }
}
