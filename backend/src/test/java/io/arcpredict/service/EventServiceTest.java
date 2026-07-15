package io.arcpredict.service;

import io.arcpredict.entity.EventEntity;

import io.arcpredict.repository.EventRepository;
import io.arcpredict.repository.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}