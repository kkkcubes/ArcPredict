package io.arcpredict.controller;

import io.arcpredict.entity.MarketEntity;
import io.arcpredict.service.MarketService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markets")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService
        marketService;

    @GetMapping
    public List<MarketEntity>
    getMarkets() {

        return marketService
            .getMarkets();
    }

    @GetMapping("/{marketId}")
    public MarketEntity
    getMarket(
        @PathVariable
        Long marketId
    ) {

        return marketService
            .getMarket(
                marketId
            );
    }

    @GetMapping("/active")
    public List<MarketEntity>
    activeMarkets() {

        return marketService
            .activeMarkets();
    }
}