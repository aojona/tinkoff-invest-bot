package ru.kirill.tinkoff.invest.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kirill.tinkoff.invest.entity.Subscriber;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    @EntityGraph(attributePaths = "currencies")
    Optional<Subscriber> findByChatId(Long chatId);

    @NonNull
    @EntityGraph(attributePaths = "currencies")
    List<Subscriber> findAll();
}
