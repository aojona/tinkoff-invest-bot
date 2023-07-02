package ru.kirill.tinkoff.invest.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kirill.tinkoff.invest.client.TinkoffClient;
import ru.kirill.tinkoff.invest.entity.Currency;
import ru.kirill.tinkoff.invest.entity.Subscriber;
import ru.kirill.tinkoff.invest.repository.CurrencyRepository;
import ru.kirill.tinkoff.invest.repository.SubscriberRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataService {

    private final CurrencyRepository currencyRepository;
    private final SubscriberRepository subscriberRepository;
    private final TinkoffClient tinkoffClient;

    @PostConstruct
    @Transactional
    public void initDatabase() {
        if (currencyRepository.findAll().isEmpty()) {
            currencyRepository.saveAll(tinkoffClient.getCurrencies());
        }
    }

    @Cacheable(value = "currency")
    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    @Cacheable(value = "currency")
    public Currency getCurrencyBiFigi(String figi) {
        return currencyRepository.findByFigi(figi).orElseThrow();
    }

    @Transactional
    @CacheEvict(value = "currency")
    public Currency updateCurrencyPrice(String figi) {
        Currency currencyToUpdate = currencyRepository.findByFigi(figi).orElseThrow();
        Currency updatedCurrency = tinkoffClient.getPrice(currencyToUpdate);
        return currencyRepository.save(updatedCurrency);
    }

    public Optional<Subscriber> findSubscriber(Long id) {
        return subscriberRepository.findByChatId(id);
    }

    @Transactional
    public Subscriber addSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    @Transactional
    public Subscriber addCurrencyToSubscriber(Long chatId, String figi) {
        return subscriberRepository
                .findByChatId(chatId)
                .map(subscriber -> {
                    Currency currency = currencyRepository.findByFigi(figi).orElseThrow();
                    subscriber.getCurrencies().add(currency);
                    return subscriberRepository.save(subscriber);
                })
                .orElseThrow();
    }

    public List<Subscriber> getSubscribers() {
        return subscriberRepository.findAll();
    }
}
