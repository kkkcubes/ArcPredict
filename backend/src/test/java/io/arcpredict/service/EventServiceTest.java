package io.arcpredict.service;

import io.arcpredict.dto.ActivityResponse;

import io.arcpredict.entity.EventEntity;
import io.arcpredict.entity.TradeEntity;

import io.arcpredict.repository.EventRepository;
import io.arcpredict.repository.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldSaveEvent() {

        EventEntity event =
            EventEntity.builder()
                .eventType("MARKET_CREATED")
                .marketId(1L)
                .txHash("0xtx")
                .blockNumber(123L)
                .payload("{}")
                .build();

        when(
            eventRepository.save(
                any(EventEntity.class)
            )
        ).thenReturn(
            event
        );

        EventEntity saved =
            eventService.saveEvent(
                "MARKET_CREATED",
                1L,
                "0xtx",
                123L,
                "{}"
            );

        assertEquals(
            "MARKET_CREATED",
            saved.getEventType()
        );

        assertEquals(
            1L,
            saved.getMarketId()
        );

        assertEquals(
            "0xtx",
            saved.getTxHash()
        );

    }

    @Test
    void shouldReturnLatestEvents() {

        EventEntity event1 =
            EventEntity.builder()
                .id(1L)
                .eventType("MARKET_CREATED")
                .marketId(1L)
                .timestamp(
                    Instant.now()
                )
                .build();

        EventEntity event2 =
            EventEntity.builder()
                .id(2L)
                .eventType("TRADE_EXECUTED")
                .marketId(2L)
                .timestamp(
                    Instant.now()
                )
                .build();

        when(
            eventRepository
                .findTop50ByOrderByTimestampDesc()
        ).thenReturn(
            List.of(
                event1,
                event2
            )
        );

        List<EventEntity> events =
            eventService.latestEvents();

        assertEquals(
            2,
            events.size()
        );

        assertEquals(
            "MARKET_CREATED",
            events.get(0).getEventType()
        );

        assertEquals(
            "TRADE_EXECUTED",
            events.get(1).getEventType()
        );

    }

    @Test
    void shouldBuildActivityFeed() {

        Instant now =
            Instant.now();

        EventEntity event =
            EventEntity.builder()
                .id(1L)
                .eventType("TRADE_EXECUTED")
                .marketId(1L)
                .txHash("0xtx")
                .blockNumber(123L)
                .timestamp(now)
                .build();

        TradeEntity trade =
            TradeEntity.builder()
                .txHash("0xtx")
                .trader("0xwallet")
                .amount(250L)
                .yesPosition(true)
                .build();

        when(
            eventRepository
                .findTop50ByOrderByTimestampDesc()
        ).thenReturn(
            List.of(event)
        );

        when(
            tradeRepository.findByTxHashIn(
                List.of("0xtx")
            )
        ).thenReturn(
            List.of(trade)
        );

        List<ActivityResponse> feed =
            eventService.getActivityFeed();

        assertEquals(
            1,
            feed.size()
        );

        ActivityResponse activity =
            feed.get(0);

        assertEquals(
            "TRADE_EXECUTED",
            activity.getEventType()
        );

        assertEquals(
            1L,
            activity.getMarketId()
        );

        assertEquals(
            "0xwallet",
            activity.getWallet()
        );

        assertEquals(
            250L,
            activity.getAmount()
        );

        assertEquals(
            "YES",
            activity.getPosition()
        );

        assertEquals(
            "0xtx",
            activity.getTxHash()
        );

        assertEquals(
            123L,
            activity.getBlockNumber()
        );

        assertEquals(
            "TRADE_EXECUTED on Market #1",
            activity.getSummary()
        );

    }

    @Test
void shouldBuildActivityFeedWithoutMatchingTrade() {

    Instant now =
        Instant.now();

    EventEntity event =
        EventEntity.builder()
            .id(1L)
            .eventType("MARKET_CREATED")
            .marketId(5L)
            .txHash("0xmissing")
            .blockNumber(999L)
            .timestamp(now)
            .build();

    when(
        eventRepository
            .findTop50ByOrderByTimestampDesc()
    ).thenReturn(
        List.of(event)
    );

    when(
        tradeRepository.findByTxHashIn(
            List.of("0xmissing")
        )
    ).thenReturn(
        List.of()
    );

    List<ActivityResponse> feed =
        eventService.getActivityFeed();

    assertEquals(
        1,
        feed.size()
    );

    ActivityResponse activity =
        feed.get(0);

    assertEquals(
        "MARKET_CREATED",
        activity.getEventType()
    );

    assertEquals(
        5L,
        activity.getMarketId()
    );

    assertEquals(
        null,
        activity.getWallet()
    );

    assertEquals(
        null,
        activity.getAmount()
    );

    assertEquals(
        null,
        activity.getPosition()
    );

    assertEquals(
        "0xmissing",
        activity.getTxHash()
    );

    assertEquals(
        999L,
        activity.getBlockNumber()
    );

    assertEquals(
        "MARKET_CREATED on Market #5",
        activity.getSummary()
    );

}

}