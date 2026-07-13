package io.arcpredict.controller;

import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Trades",
    description = "Trade management APIs"
)
@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeRepository
        tradeRepository;

    @Operation(
        summary = "Get all trades",
        description = "Returns all executed trades."
    )
    @GetMapping
    public List<TradeEntity>
    getTrades() {

        return tradeRepository
            .findAll();

    }

    @Operation(
        summary = "Get trades (paginated)",
        description = "Returns trades using pagination."
    )
    @GetMapping("/page")
    public Page<TradeEntity>
    getTradesPage(

        @RequestParam(
            defaultValue = "0"
        )
        int page,

        @RequestParam(
            defaultValue = "20"
        )
        int size

    ) {

        Pageable pageable =
            PageRequest.of(
                page,
                size
            );

        return tradeRepository
            .findAll(
                pageable
            );

    }

    @Operation(
        summary = "Get trades by market",
        description = "Returns all trades for a specific prediction market."
    )
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

    @Operation(
        summary = "Get trades by trader",
        description = "Returns all trades executed by a specific wallet."
    )
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