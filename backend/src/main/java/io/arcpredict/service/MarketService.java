package io.arcpredict.service;

import io.arcpredict.entity.MarketEntity;
import io.arcpredict.repository.MarketRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository
        marketRepository;

    public List<MarketEntity>
    getMarkets() {

        return marketRepository
            .findAll();
    }

    public MarketEntity getMarket(
        Long marketId
    ) {

        return marketRepository
            .findById(marketId)
            .orElseThrow();
    }

    public MarketEntity save(
        MarketEntity market
    ) {

        return marketRepository
            .save(market);
    }

    public List<MarketEntity>
    activeMarkets() {

        return marketRepository
            .findByResolved(false);
    }
}