package io.arcpredict.service;

import io.arcpredict.dto.MarketSentimentResponse;
import io.arcpredict.entity.MarketEntity;
import io.arcpredict.repository.MarketRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketSentimentService {

    private final MarketRepository marketRepository;

    public List<MarketSentimentResponse> getMarketSentiment() {

        return marketRepository
            .findAll()
            .stream()
            .map(this::toResponse)
            .toList();

    }

    private MarketSentimentResponse toResponse(
        MarketEntity market
    ) {

        long yesPool =
            market.getYesPool() == null
                ? 0L
                : market.getYesPool();

        long noPool =
            market.getNoPool() == null
                ? 0L
                : market.getNoPool();

        long totalPool =
            yesPool + noPool;

        double yesPercentage =
            totalPool == 0
                ? 0.0
                : (yesPool * 100.0) / totalPool;

        double noPercentage =
            totalPool == 0
                ? 0.0
                : (noPool * 100.0) / totalPool;

        return MarketSentimentResponse
            .builder()
            .marketId(
                market.getMarketId()
            )
            .yesPool(
                yesPool
            )
            .noPool(
                noPool
            )
            .totalPool(
                totalPool
            )
            .yesPercentage(
                yesPercentage
            )
            .noPercentage(
                noPercentage
            )
            .build();

    }
}