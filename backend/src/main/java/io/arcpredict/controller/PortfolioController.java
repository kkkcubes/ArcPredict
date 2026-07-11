package io.arcpredict.controller;

import io.arcpredict.dto.PortfolioAnalyticsResponse;
import io.arcpredict.dto.PortfolioResponse;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;
import io.arcpredict.service.PortfolioService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final TradeRepository
        tradeRepository;

    private final PortfolioService
        portfolioService;

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

}