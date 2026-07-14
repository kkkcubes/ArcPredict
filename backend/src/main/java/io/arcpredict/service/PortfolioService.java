package io.arcpredict.service;

import io.arcpredict.dto.PortfolioAnalyticsResponse;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.entity.WalletPositionEntity;
import io.arcpredict.repository.TradeRepository;
import io.arcpredict.repository.WalletRepository;
import io.arcpredict.dto.WalletPositionResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final WalletRepository
        walletRepository;

    private final TradeRepository
        tradeRepository;

    public List<WalletPositionEntity>
    getPortfolio(
        String wallet
    ) {

        return walletRepository
            .findByWalletAddress(
                wallet
            );

    }

    public WalletPositionEntity
    savePosition(
        WalletPositionEntity entity
    ) {

        return walletRepository
            .save(entity);

    }

    public PortfolioAnalyticsResponse
    getPortfolioAnalytics(
        String wallet
    ) {

        List<TradeEntity>
            trades =
                tradeRepository
                    .findByTrader(
                        wallet.toLowerCase()
                    );

                    List<WalletPositionEntity>
    positions =
        walletRepository
            .findByWalletAddress(
                wallet.toLowerCase()
            );

        long totalInvested = 0;
        long yesPositions = 0;
        long noPositions = 0;

        for (
            TradeEntity trade :
            trades
        ) {

            totalInvested +=
                trade.getAmount();

            if (
                Boolean.TRUE.equals(
                    trade.getYesPosition()
                )
            ) {

                yesPositions++;

            } else {

                noPositions++;

            }

        }

        double averageEntryPrice =
            trades.isEmpty()
                ? 0
                : (double) totalInvested
                    / trades.size();

                    long currentValue =

    positions
        .stream()
        .mapToLong(

            position ->

                position.getCurrentValue() == null
                    ? 0L
                    : position.getCurrentValue()

        )
        .sum();

long claimableRewards =

    positions
        .stream()
        .mapToLong(

            position ->

                position.getClaimableRewards() == null
                    ? 0L
                    : position.getClaimableRewards()

        )
        .sum();

long unrealizedPnL =
    currentValue
        - totalInvested;

double roi =

    totalInvested == 0

        ? 0

        : ((double) unrealizedPnL
            / totalInvested)
            * 100;

        return PortfolioAnalyticsResponse
            .builder()
            .wallet(
                wallet
            )
            .totalInvested(
                totalInvested
            )
            .currentValue(
    currentValue
)
.unrealizedPnL(
    unrealizedPnL
)
.realizedPnL(
    claimableRewards
)
.roi(
    roi
)
            .totalTrades(
                (long) trades.size()
            )
            .yesPositions(
                yesPositions
            )
            .noPositions(
                noPositions
            )
            .averageEntryPrice(
                averageEntryPrice
            )
            .build();

    }

    public List<WalletPositionResponse>
getWalletPositions(
    String wallet
) {

    return walletRepository
        .findByWalletAddress(
            wallet.toLowerCase()
        )
        .stream()
        .map(position ->

            WalletPositionResponse
                .builder()
                .marketId(
                    position.getMarketId()
                )
                .yesPosition(
                    position.getYesPosition()
                )
                .shares(
                    position.getShares()
                )
                .investedAmount(
                    position.getInvestedAmount()
                )
                .currentValue(
                    position.getCurrentValue()
                )
                .claimableRewards(
                    position.getClaimableRewards()
                )
                .claimed(
                    position.getClaimed()
                )
                .winner(
                    position.getWinner()
                )
                .settled(
                    position.getSettled()
                )
                .claimedAmount(
                    position.getClaimedAmount()
                )
                .build()

        )
        .collect(
            Collectors.toList()
        );

}

}