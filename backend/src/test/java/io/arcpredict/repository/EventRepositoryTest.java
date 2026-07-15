package io.arcpredict.repository;

import io.arcpredict.entity.EventEntity;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void shouldFindEventsByType() {

        EventEntity event1 =
            EventEntity.builder()
                .eventType("MARKET_CREATED")
                .marketId(1L)
                .txHash("0x111")
                .blockNumber(100L)
                .payload("{}")
                .timestamp(
                    Instant.now()
                )
                .build();

        EventEntity event2 =
            EventEntity.builder()
                .eventType("TRADE_EXECUTED")
                .marketId(2L)
                .txHash("0x222")
                .blockNumber(200L)
                .payload("{}")
                .timestamp(
                    Instant.now()
                )
                .build();

        eventRepository.save(event1);
        eventRepository.save(event2);

        assertEquals(
            1,
            eventRepository
                .findByEventType(
                    "MARKET_CREATED"
                )
                .size()
        );

    }

    @Test
void shouldFindEventsByMarketId() {

    EventEntity event1 =
        EventEntity.builder()
            .eventType("MARKET_CREATED")
            .marketId(10L)
            .txHash("0xaaa")
            .blockNumber(100L)
            .payload("{}")
            .timestamp(
                Instant.now()
            )
            .build();

    EventEntity event2 =
        EventEntity.builder()
            .eventType("TRADE_EXECUTED")
            .marketId(10L)
            .txHash("0xbbb")
            .blockNumber(101L)
            .payload("{}")
            .timestamp(
                Instant.now()
            )
            .build();

    EventEntity event3 =
        EventEntity.builder()
            .eventType("MARKET_RESOLVED")
            .marketId(20L)
            .txHash("0xccc")
            .blockNumber(102L)
            .payload("{}")
            .timestamp(
                Instant.now()
            )
            .build();

    eventRepository.save(event1);
    eventRepository.save(event2);
    eventRepository.save(event3);

    assertEquals(
        2,
        eventRepository
            .findByMarketId(10L)
            .size()
    );

    assertEquals(
        1,
        eventRepository
            .findByMarketId(20L)
            .size()
    );

}

@Test
void shouldReturnLatestEvents() {

    Instant now =
        Instant.now();

    EventEntity older =
        EventEntity.builder()
            .eventType("MARKET_CREATED")
            .marketId(1L)
            .txHash("0x1")
            .blockNumber(100L)
            .payload("{}")
            .timestamp(
                now.minusSeconds(60)
            )
            .build();

    EventEntity newer =
        EventEntity.builder()
            .eventType("TRADE_EXECUTED")
            .marketId(2L)
            .txHash("0x2")
            .blockNumber(101L)
            .payload("{}")
            .timestamp(now)
            .build();

    eventRepository.save(older);
    eventRepository.save(newer);

    assertEquals(
        "TRADE_EXECUTED",
        eventRepository
            .findTop50ByOrderByTimestampDesc()
            .get(0)
            .getEventType()
    );

}

}