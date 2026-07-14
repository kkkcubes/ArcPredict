package io.arcpredict.service;

import io.arcpredict.dto.EventMessage;
import io.arcpredict.dto.LeaderboardEntry;
import io.arcpredict.dto.PortfolioResponse;

import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.entity.EventEntity;
import io.arcpredict.entity.MarketEntity;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.entity.WalletPositionEntity;

import io.arcpredict.repository.EventRepository;
import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;
import io.arcpredict.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;


@Service
@RequiredArgsConstructor
public class MarketSyncService {

    private static final Logger log =
        LoggerFactory.getLogger(
            MarketSyncService.class
        );

    private final MarketRepository
        marketRepository;

    private final TradeRepository
        tradeRepository;

    private final WalletRepository
        walletRepository;

    private final EventRepository
        eventRepository;

    private final WebSocketBroadcastService
        webSocketBroadcastService;

    private final LeaderboardService
        leaderboardService;

    private final AnalyticsService
        analyticsService;

        @Value(
    "${protocol.settlement.fee-percentage}"
)
private long settlementFeePercentage;

    public void saveMarket(
        MarketEntity market
    ) {

        marketRepository.save(
            market
        );

        webSocketBroadcastService
            .broadcastMarket(
                market
            );
    }

    public void saveTrade(
        Long marketId,
        String trader,
        Boolean side,
        Long amount,
        String txHash,
        Long blockNumber
    ) {

        log.info(
    "saveTrade() called: trader={}, marketId={}, side={}, amount={}",
    trader,
    marketId,
    side,
    amount
);

        TradeEntity trade =
            TradeEntity.builder()
                .marketId(
                    marketId
                )
                .trader(
                    trader
                )
                .yesPosition(
                    side
                )
                .amount(
                    amount
                )
                .txHash(
                    txHash
                )
                .blockNumber(
                    blockNumber
                )
                .timestamp(
                    Instant.now()
                )
                .build();

        tradeRepository.save(
            trade
        );

        Optional<WalletPositionEntity>
    existingPosition =

        walletRepository
            .findByWalletAddressAndMarketIdAndYesPosition(
                trader.toLowerCase(),
                marketId,
                side
            );

WalletPositionEntity position =

    existingPosition.orElse(

        WalletPositionEntity
            .builder()
            .walletAddress(
                trader.toLowerCase()
            )
            .marketId(
                marketId
            )
            .yesPosition(
                side
            )
            .shares(
                0L
            )
            .investedAmount(
                0L
            )
            .currentValue(
                0L
            )
            .claimableRewards(
                0L
            )
            .claimed(
                false
            )
            .winner(
                false
            )
            .settled(
                false
            )
            .claimedAmount(
                0L
            )
            .build()

    );

position.setShares(
    position.getShares()
        + amount
);

position.setInvestedAmount(
    position.getInvestedAmount()
        + amount
);

position.setCurrentValue(
    position.getInvestedAmount()
);

log.info(
    "Saving wallet position for {}",
    position.getWalletAddress()
);

walletRepository.save(
    position
);

log.info(
    "Wallet position saved successfully"
);

        MarketEntity market =
            marketRepository
                .findById(marketId)
                .orElse(null);

        if (market != null) {

            if (
                Boolean.TRUE.equals(side)
            ) {

                market.setYesPool(
                    market.getYesPool()
                        + amount
                );

            } else {

                market.setNoPool(
                    market.getNoPool()
                        + amount
                );

            }

            market.setTotalVolume(

                (market.getTotalVolume() == null
                    ? 0L
                    : market.getTotalVolume())

                + amount
            );

            List<TradeEntity>
                marketTrades =
                    tradeRepository
                        .findByMarketId(
                            marketId
                        );

            long participants =
                marketTrades
                    .stream()
                    .map(
                        TradeEntity::getTrader
                    )
                    .distinct()
                    .count();

            market.setParticipants(
                participants
            );

            market.setUpdatedAt(
                Instant.now()
            );

            marketRepository.save(
                market
            );

        }

        List<TradeEntity> trades =
            tradeRepository.findByTrader(
                trader.toLowerCase()
            );

        long invested = 0;
        long yes = 0;
        long no = 0;

        for (
            TradeEntity t :
            trades
        ) {

            invested +=
                t.getAmount();

            if (
                Boolean.TRUE.equals(
                    t.getYesPosition()
                )
            ) {

                yes++;

            } else {

                no++;

            }

        }

        PortfolioResponse portfolio =
            PortfolioResponse.builder()
                .wallet(
                    trader
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

        webSocketBroadcastService
            .broadcastPortfolio(
                portfolio
            );

        List<LeaderboardEntry>
            leaderboard =
                leaderboardService
                    .getLeaderboard();

        webSocketBroadcastService
            .broadcastLeaderboard(
                leaderboard
            );

        AnalyticsEntity analytics =
            analyticsService
                .getAnalytics();

        webSocketBroadcastService
            .broadcastAnalytics(
                analytics
            );

        if (market != null) {

            webSocketBroadcastService
                .broadcastMarket(
                    market
                );

        }

        EventMessage message =
            EventMessage.builder()
                .eventType(
                    "TRADE_EXECUTED"
                )
                .marketId(
                    marketId
                )
                .amount(
                    amount
                )
                .wallet(
                    trader
                )
                .timestamp(
                    Instant.now().toString()
                )
                .build();

        webSocketBroadcastService
            .broadcastTrade(
                message
            );
    }

        public void saveEvent(
        String eventType,
        Long marketId,
        String txHash,
        Long blockNumber,
        String payload
    ) {

        log.info(
            "Saving event: {} for market {}",
            eventType,
            marketId
        );

        EventEntity event =
            EventEntity.builder()
                .eventType(
                    eventType
                )
                .marketId(
                    marketId
                )
                .txHash(
                    txHash
                )
                .blockNumber(
                    blockNumber
                )
                .payload(
                    payload
                )
                .timestamp(
                    Instant.now()
                )
                .build();

        eventRepository.save(
            event
        );

        log.info(
            "Event saved to database: {}",
            txHash
        );

        webSocketBroadcastService
            .broadcastEvent(
                event
            );

        log.info(
            "Event broadcast completed"
        );

    }

       public void resolveMarket(
    Long marketId,
    Boolean outcome
) {

    List<WalletPositionEntity>
        positions =

        walletRepository
            .findByMarketId(
                marketId
            );

            MarketEntity market =

    marketRepository
        .findById(
            marketId
        )
        .orElse(null);

if (
    market == null
) {
    return;
}

long totalWinningShares = 0L;

for (

    WalletPositionEntity position :

    positions

) {

    boolean winner =

        Boolean.valueOf(

            position.getYesPosition()

        ).equals(

            outcome

        );

    if (
        winner
    ) {

        totalWinningShares +=
            position.getShares();

    }

}

log.info(
    "Total winning shares for market {}: {}",
    marketId,
    totalWinningShares
);

long rewardPool =
    Boolean.TRUE.equals(
        outcome
    )
    ? market.getNoPool()
    : market.getYesPool();

    long totalProtocolFees = 0L;

log.info(
    "Reward pool for market {}: {}",
    marketId,
    rewardPool
);

    for (

        WalletPositionEntity position :

        positions

    ) {

        boolean winner =

            Boolean.valueOf(

                position.getYesPosition()

            ).equals(

                outcome

            );

        position.setWinner(
            winner
        );

        position.setSettled(
            true
        );

        position.setClaimableRewards(
            0L
        );

        position.setClaimed(
            false
        );

        position.setClaimedAmount(
            0L
        );

        position.setClaimedAt(
            null
        );

        // Settlement Formula
//
// Future implementation:
//
// Reward =
// Original Stake
// + Proportional Share
// of Losing Pool
//
// This placeholder currently
// returns the original stake.

long winnerShare = 0L;

if (
    winner
    &&
    totalWinningShares > 0
) {

    winnerShare =

        rewardPool
            * position.getShares()
            / totalWinningShares;

}

long protocolFee =

    winnerShare
        * settlementFeePercentage
        / 100;

        totalProtocolFees +=
    protocolFee;

log.info(
    "Winner share for wallet {}: {}",
    position.getWalletAddress(),
    winnerShare
);

log.info(
    "Protocol fee for wallet {}: {}",
    position.getWalletAddress(),
    protocolFee
);


        if (
    winner
) {

    // TODO:
// Replace the placeholder reward assignment
// with the protocol's actual settlement
// calculation in Phase 18.28.

    long calculatedReward =

    position.getInvestedAmount()
    +
    winnerShare
    -
    protocolFee;

    position.setClaimableRewards(
        calculatedReward
);
log.info(
    "Calculated reward for wallet {}: {}",
    position.getWalletAddress(),
    calculatedReward
);

}

    if (
    winner
) {

    log.info(
        "Wallet {} is eligible to claim {} rewards",
        position.getWalletAddress(),
        position.getClaimableRewards()
    );

}

        if (
    !winner
) {

    position.setCurrentValue(
        0L
    );

}

    log.info(
    "Settled wallet {} on market {} (winner={})",
    position.getWalletAddress(),
    marketId,
    winner
);

        walletRepository.save(
            position
        );

    }

    log.info(
    "Total protocol fees collected for market {}: {}",
    marketId,
    totalProtocolFees
);

market.setProtocolFees(
    totalProtocolFees
);

marketRepository.save(
    market
);

    log.info(
    "Settlement completed for market {}",
    marketId
);

}

public void markRewardClaimed(
    Long marketId,
    String trader,
    Long claimedAmount
) {

    WalletPositionEntity position =

        walletRepository
            .findByWalletAddressAndMarketId(
                trader.toLowerCase(),
                marketId
            )
            .orElse(null);

    if (
        position == null
    ) {

        log.warn(
            "Wallet position not found for {} on market {}",
            trader,
            marketId
        );

        return;

    }

    if (
    !Boolean.TRUE.equals(
        position.getSettled()
    )
) {

    log.warn(
        "Wallet {} is not settled for market {}",
        trader,
        marketId
    );

    return;

}

if (
    Boolean.TRUE.equals(
        position.getClaimed()
    )
) {

    log.info(
        "Reward already claimed for wallet {} on market {}",
        trader,
        marketId
    );

    return;

}

    position.setClaimed(
        true
    );

    position.setClaimableRewards(
        0L
    );

    position.setClaimedAmount(
        claimedAmount
    );

    position.setClaimedAt(
        Instant.now()
    );

    walletRepository.save(
        position
    );

    List<TradeEntity> trades =

    tradeRepository
        .findByTrader(
            trader.toLowerCase()
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

PortfolioResponse portfolio =

    PortfolioResponse
        .builder()
        .wallet(
            trader
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

webSocketBroadcastService
    .broadcastPortfolio(
        portfolio
    );

    log.info(
        "Reward claimed by wallet {} for market {}",
        trader,
        marketId
    );

}

}