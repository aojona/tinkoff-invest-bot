package ru.kirill.tinkoff.invest.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.InstrumentStatus;
import ru.tinkoff.piapi.core.InstrumentsService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TinkoffClient {

    private final InstrumentsService instrumentsService;

    public List<Currency> getCurrencies() {
        return instrumentsService.getCurrenciesSync(InstrumentStatus.INSTRUMENT_STATUS_ALL);
    }

    public Currency getCurrency(String ticker) {
        return instrumentsService.getCurrencyByTickerSync(ticker, "CETS");
    }
}
