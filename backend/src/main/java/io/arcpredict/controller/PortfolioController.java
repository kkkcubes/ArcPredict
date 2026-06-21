package io.arcpredict.controller;

import io.arcpredict.dto.PortfolioResponse;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PortfolioController {

    private final TradeRepository tradeRepository;

    @GetMapping("/portfolio/{wallet}")
    public PortfolioResponse portfolio(
        @PathVariable String wallet
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
            .wallet(wallet)
            .totalInvested(invested)
            .yesPositions(yes)
            .noPositions(no)
            .totalTrades(
                (long) trades.size()
            )
            .build();
    }
}