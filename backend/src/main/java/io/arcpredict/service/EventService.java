package io.arcpredict.service;

import io.arcpredict.entity.EventEntity;
import io.arcpredict.repository.EventRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository
        eventRepository;

    public EventEntity saveEvent(
        String eventType,
        Long marketId,
        String txHash,
        Long blockNumber,
        String payload
    ) {

        EventEntity event =
            EventEntity.builder()
                .eventType(eventType)
                .marketId(marketId)
                .txHash(txHash)
                .blockNumber(blockNumber)
                .payload(payload)
                .timestamp(
                    Instant.now()
                )
                .build();

        return eventRepository
            .save(event);
    }

    public List<EventEntity>
    latestEvents() {

        return eventRepository
            .findTop50ByOrderByTimestampDesc();
    }
}