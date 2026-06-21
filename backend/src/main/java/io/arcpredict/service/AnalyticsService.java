package io.arcpredict.service;

import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.entity.MarketEntity;

import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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
                    m -> m.getYesPool()
                )
                .sum();

        long noPool =
            markets.stream()
                .mapToLong(
                    m -> m.getNoPool()
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
}