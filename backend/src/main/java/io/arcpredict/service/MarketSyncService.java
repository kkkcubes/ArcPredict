package io.arcpredict.service;

import io.arcpredict.dto.EventMessage;
import io.arcpredict.dto.LeaderboardEntry;
import io.arcpredict.dto.PortfolioResponse;

import io.arcpredict.entity.EventEntity;
import io.arcpredict.entity.MarketEntity;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.entity.AnalyticsEntity;

import io.arcpredict.repository.EventRepository;
import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketSyncService {

    private final MarketRepository marketRepository;

    private final TradeRepository tradeRepository;

    private final EventRepository eventRepository;

    private final WebSocketBroadcastService webSocketBroadcastService;

    private final LeaderboardService leaderboardService;    

    private final AnalyticsService analyticsService;

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

        webSocketBroadcastService
            .broadcastEvent(
                event
            );
    }
}