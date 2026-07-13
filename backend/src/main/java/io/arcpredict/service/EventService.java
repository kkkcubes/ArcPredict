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

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    List<EventEntity> events =
        eventRepository
            .findTop50ByOrderByTimestampDesc();

    Set<String> txHashes =
        events.stream()

            .map(
                EventEntity::getTxHash
            )

            .filter(
                txHash -> txHash != null
                    && !txHash.isBlank()
            )

            .collect(
                Collectors.toSet()
            );

    Map<String, TradeEntity> tradesByTxHash =
        new HashMap<>();

    if (!txHashes.isEmpty()) {

        tradeRepository

            .findByTxHashIn(
                List.copyOf(txHashes)
            )

            .forEach(

                trade ->

                    tradesByTxHash.put(

                        trade.getTxHash(),

                        trade

                    )

            );

    }

    return events

        .stream()

        .map(event -> {

            TradeEntity trade =

                tradesByTxHash.get(
                    event.getTxHash()
                );

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