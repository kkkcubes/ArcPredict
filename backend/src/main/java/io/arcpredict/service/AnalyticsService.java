package io.arcpredict.service;

import io.arcpredict.dto.AnalyticsHistoryResponse;
import io.arcpredict.dto.ChartPoint;
import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.entity.MarketEntity;

import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final MarketRepository
        marketRepository;

    private final TradeRepository
        tradeRepository;

    public AnalyticsEntity
    getAnalytics() {

        List<MarketEntity>
            markets =
            marketRepository.findAll();

        long totalVolume =
            markets.stream()
                .mapToLong(
                    m ->
                        m.getTotalVolume() == null
                            ? 0
                            : m.getTotalVolume()
                )
                .sum();

        long yesPool =
            markets.stream()
                .mapToLong(
                    MarketEntity::getYesPool
                )
                .sum();

        long noPool =
            markets.stream()
                .mapToLong(
                    MarketEntity::getNoPool
                )
                .sum();

        double bullish =
            (yesPool + noPool) == 0
                ? 50
                : ((double) yesPool /
                    (yesPool + noPool))
                    * 100;

        return AnalyticsEntity
            .builder()
            .totalMarkets(
                (long) markets.size()
            )
            .totalVolume(
                totalVolume
            )
            .totalTraders(
                tradeRepository.count()
            )
            .resolvedMarkets(
                marketRepository
                    .findByResolved(true)
                    .stream()
                    .count()
            )
            .openInterest(
                yesPool + noPool
            )
            .bullishPercentage(
                bullish
            )
            .bearishPercentage(
                100 - bullish
            )
            .snapshotTime(
                Instant.now()
            )
            .build();

    }

    public AnalyticsHistoryResponse
    getAnalyticsHistory() {

        Map<LocalDate, Long>
            volumeByDay =

            tradeRepository
                .findAll()
                .stream()
                .collect(

                    Collectors.groupingBy(

                        trade ->

                            trade
                                .getTimestamp()
                                .atZone(
                                    ZoneId.systemDefault()
                                )
                                .toLocalDate(),

                        Collectors.summingLong(
                            trade ->
                                trade.getAmount()
                        )

                    )

                );

        Map<LocalDate, Long>
            tradesByDay =

            tradeRepository
                .findAll()
                .stream()
                .collect(

                    Collectors.groupingBy(

                        trade ->

                            trade
                                .getTimestamp()
                                .atZone(
                                    ZoneId.systemDefault()
                                )
                                .toLocalDate(),

                        Collectors.counting()

                    )

                );

        Map<LocalDate, Long>
            marketsByDay =

            marketRepository
                .findAll()
                .stream()
                .collect(

                    Collectors.groupingBy(

                        market ->

                            market
                                .getCreatedAt()
                                .atZone(
                                    ZoneId.systemDefault()
                                )
                                .toLocalDate(),

                        Collectors.counting()

                    )

                );

        Map<String, Long>
            categories =

            marketRepository
                .findAll()
                .stream()

                .collect(

                    Collectors.groupingBy(

                        market ->

                            market.getCategory() == null
                                ? "Unknown"
                                : market.getCategory(),

                        Collectors.counting()

                    )

                );

        return AnalyticsHistoryResponse
            .builder()

            .dailyVolume(

                volumeByDay
                    .entrySet()
                    .stream()
                    .sorted(
                        Map.Entry.comparingByKey()
                    )
                    .map(

                        entry ->

                            ChartPoint
                                .builder()
                                .date(
                                    entry
                                        .getKey()
                                        .toString()
                                )
                                .value(
                                    entry.getValue()
                                )
                                .build()

                    )
                    .toList()

            )

            .dailyTrades(

                tradesByDay
                    .entrySet()
                    .stream()
                    .sorted(
                        Map.Entry.comparingByKey()
                    )
                    .map(

                        entry ->

                            ChartPoint
                                .builder()
                                .date(
                                    entry
                                        .getKey()
                                        .toString()
                                )
                                .value(
                                    entry.getValue()
                                )
                                .build()

                    )
                    .toList()

            )

            .dailyMarkets(

                marketsByDay
                    .entrySet()
                    .stream()
                    .sorted(
                        Map.Entry.comparingByKey()
                    )
                    .map(

                        entry ->

                            ChartPoint
                                .builder()
                                .date(
                                    entry
                                        .getKey()
                                        .toString()
                                )
                                .value(
                                    entry.getValue()
                                )
                                .build()

                    )
                    .toList()

            )

            .categoryBreakdown(

                categories
                    .entrySet()
                    .stream()

                    .sorted(
                        Map.Entry.comparingByKey()
                    )

                    .map(

                        entry ->

                            ChartPoint
                                .builder()

                                .date(
                                    entry.getKey()
                                )

                                .value(
                                    entry.getValue()
                                )

                                .build()

                    )

                    .toList()

            )

            .build();

    }

}