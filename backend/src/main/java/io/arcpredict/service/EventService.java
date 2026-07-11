package io.arcpredict.service;

import io.arcpredict.dto.ActivityResponse;
import io.arcpredict.entity.EventEntity;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.EventRepository;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository
        eventRepository;

    private final TradeRepository
        tradeRepository;

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

    public List<ActivityResponse>
    getActivityFeed() {

        return eventRepository
            .findTop50ByOrderByTimestampDesc()
            .stream()
            .map(event -> {

                TradeEntity trade =
                    tradeRepository
                        .findByTxHash(
                            event.getTxHash()
                        )
                        .orElse(null);

                return ActivityResponse
                    .builder()
                    .id(
                        event.getId()
                    )
                    .eventType(
                        event.getEventType()
                    )
                    .marketId(
                        event.getMarketId()
                    )
                    .wallet(
                        trade == null
                            ? null
                            : trade.getTrader()
                    )
                    .amount(
                        trade == null
                            ? null
                            : trade.getAmount()
                    )
                    .position(
                        trade == null
                            ? null
                            : (
                                trade.getYesPosition()
                                    ? "YES"
                                    : "NO"
                            )
                    )
                    .txHash(
                        event.getTxHash()
                    )
                    .blockNumber(
                        event.getBlockNumber()
                    )
                    .timestamp(
                        event.getTimestamp()
                    )
                    .summary(
                        event.getEventType()
                            + " on Market #"
                            + event.getMarketId()
                    )
                    .build();

            })
            .toList();

    }

}