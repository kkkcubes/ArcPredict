package io.arcpredict.controller;

import io.arcpredict.dto.PortfolioAnalyticsResponse;
import io.arcpredict.dto.PortfolioResponse;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;
import io.arcpredict.service.PortfolioService;
import io.arcpredict.dto.WalletPositionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Portfolio",
    description = "Portfolio management APIs"
)

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final TradeRepository
        tradeRepository;

    private final PortfolioService
        portfolioService;
    
        @Operation(
    summary = "Get portfolio",
    description = "Returns the portfolio summary for a wallet."
)
    @GetMapping("/{wallet}")
    public PortfolioResponse portfolio(
        @PathVariable
        String wallet
    ) {

        List<TradeEntity> trades =
            tradeRepository.findByTrader(
                wallet.toLowerCase()
            );

        long invested = 0;
        long yes = 0;
        long no = 0;

        for (
            TradeEntity trade :
            trades
        ) {

            invested +=
                trade.getAmount();

            if (
                Boolean.TRUE.equals(
                    trade.getYesPosition()
                )
            ) {

                yes++;

            } else {

                no++;

            }

        }

        return PortfolioResponse
            .builder()
            .wallet(
                wallet
            )
            .totalInvested(
                invested
            )
            .yesPositions(
                yes
            )
            .noPositions(
                no
            )
            .totalTrades(
                (long) trades.size()
            )
            .build();

    }

    @Operation(
    summary = "Get portfolio analytics",
    description = "Returns portfolio analytics for a wallet."
)

    @GetMapping("/analytics/{wallet}")
    public PortfolioAnalyticsResponse
    portfolioAnalytics(
        @PathVariable
        String wallet
    ) {

        return portfolioService
            .getPortfolioAnalytics(
                wallet
            );

    }

    @Operation(
    summary = "Get wallet positions",
    description = "Returns all positions for a wallet."
)
@GetMapping("/positions/{wallet}")
public List<WalletPositionResponse>
walletPositions(
    @PathVariable
    String wallet
) {

    return portfolioService
        .getWalletPositions(
            wallet
        );

}

}