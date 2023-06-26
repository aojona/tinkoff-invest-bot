package ru.kirill.tinkoff.invest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kirill.tinkoff.invest.config.properties.TinkoffProperties;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;

@Configuration
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class TinkoffConfig {

    private final TinkoffProperties tinkoffProperties;

    @Bean
    public InvestApi investApi() {
        String token = tinkoffProperties.token();
        return tinkoffProperties.sandbox()
                ? InvestApi.createSandbox(token)
                : InvestApi.create(token);
    }

    @Bean
    public InstrumentsService instrumentsService(InvestApi investApi) {
        return investApi.getInstrumentsService();
    }
}
