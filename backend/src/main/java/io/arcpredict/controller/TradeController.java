package io.arcpredict.controller;

import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeRepository
        tradeRepository;

    @GetMapping
    public List<TradeEntity>
    getTrades() {

        return tradeRepository
            .findAll();
    }

    @GetMapping("/market/{marketId}")
    public List<TradeEntity>
    getTradesByMarket(
        @PathVariable
        Long marketId
    ) {

        return tradeRepository
            .findByMarketId(
                marketId
            );
    }

    @GetMapping("/trader/{wallet}")
    public List<TradeEntity>
    getTradesByTrader(
        @PathVariable
        String wallet
    ) {

        return tradeRepository
            .findByTrader(
                wallet
            );
    }
}