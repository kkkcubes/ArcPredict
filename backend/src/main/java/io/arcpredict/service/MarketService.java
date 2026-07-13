package io.arcpredict.service;

import io.arcpredict.entity.MarketEntity;
import io.arcpredict.repository.MarketRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public Page<MarketEntity>
    getMarketsPage(

        Pageable pageable

    ) {

        return marketRepository
            .findAll(
                pageable
            );

    }

    public MarketEntity
    getMarket(
        Long marketId
    ) {

        return marketRepository
            .findById(marketId)
            .orElseThrow();

    }

    public MarketEntity
    save(
        MarketEntity market
    ) {

        return marketRepository
            .save(
                market
            );

    }

    public List<MarketEntity>
    activeMarkets() {

        return marketRepository
            .findByResolved(
                false
            );

    }

}