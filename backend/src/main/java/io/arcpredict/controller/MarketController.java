package io.arcpredict.controller;

import io.arcpredict.entity.MarketEntity;
import io.arcpredict.service.MarketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Markets",
    description = "Prediction market APIs"
)
@RestController
@RequestMapping("/api/markets")
@RequiredArgsConstructor
@Validated
public class MarketController {

    private final MarketService
        marketService;

    @Operation(
        summary = "Get all markets",
        description = "Returns all prediction markets."
    )
    @GetMapping
    public List<MarketEntity>
    getMarkets() {

        return marketService
            .getMarkets();

    }

    @Operation(
        summary = "Get markets (paginated)",
        description = "Returns prediction markets using pagination."
    )
    @GetMapping("/page")
    public Page<MarketEntity>
    getMarketsPage(

        @RequestParam(
            defaultValue = "0"
        )
        @Min(0)
        int page,

        @RequestParam(
            defaultValue = "20"
        )
        @Min(1)
        @Max(100)
        int size

    ) {

        Pageable pageable =
            PageRequest.of(
                page,
                size
            );

        return marketService
            .getMarketsPage(
                pageable
            );

    }

    @Operation(
        summary = "Get market by ID",
        description = "Returns a prediction market by its ID."
    )
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

    @Operation(
        summary = "Get active markets",
        description = "Returns all unresolved prediction markets."
    )
    @GetMapping("/active")
    public List<MarketEntity>
    activeMarkets() {

        return marketService
            .activeMarkets();

    }

}