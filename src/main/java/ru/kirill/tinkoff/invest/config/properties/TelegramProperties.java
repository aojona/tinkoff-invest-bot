package ru.kirill.tinkoff.invest.config.properties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@ConfigurationProperties(prefix = "telegram.bot")
public record TelegramProperties(String name,
                                 String webHookPath,
                                 String token) {
    @PostConstruct
    void init() {
        log.info("Telegram properties is initialized: {}", this);
    }
}
