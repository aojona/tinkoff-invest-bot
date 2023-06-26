package ru.kirill.tinkoff.invest.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@ConfigurationProperties(prefix = "tinkoff")
public record TinkoffProperties(String token,
                                boolean sandbox) {
    @PostConstruct
    void init() {
        log.info("Tinkoff properties is initialized: {}", this);
    }
}

