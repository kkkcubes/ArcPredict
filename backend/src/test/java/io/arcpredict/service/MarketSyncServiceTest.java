package io.arcpredict.service;

import io.arcpredict.dto.LeaderboardEntry;

import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.entity.EventEntity;
import io.arcpredict.entity.MarketEntity;
import io.arcpredict.entity.TradeEntity;
import io.arcpredict.entity.WalletPositionEntity;

import io.arcpredict.repository.EventRepository;
import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;
import io.arcpredict.repository.WalletRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketSyncServiceTest {

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private WebSocketBroadcastService webSocketBroadcastService;

    @Mock
    private LeaderboardService leaderboardService;

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private MarketSyncService marketSyncService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldSaveMarketAndBroadcast() {

        MarketEntity market =
            MarketEntity.builder()
                .marketId(1L)
                .question("Will BTC reach $200k?")
                .build();

        marketSyncService.saveMarket(
            market
        );

        verify(marketRepository)
            .save(market);

        verify(webSocketBroadcastService)
            .broadcastMarket(market);

    }

    @Test
    void shouldIgnoreDuplicateTrade() {

        TradeEntity existingTrade =
            TradeEntity.builder()
                .txHash("0xabc")
                .build();

        when(
            tradeRepository.findByTxHash(
                "0xabc"
            )
        ).thenReturn(
            Optional.of(existingTrade)
        );

        marketSyncService.saveTrade(
            1L,
            "0xWallet",
            true,
            100L,
            "0xabc",
            12345L
        );

        verify(
            tradeRepository,
            never()
        ).save(any());

        verify(
            walletRepository,
            never()
        ).save(any());

        verify(
            marketRepository,
            never()
        ).save(any());

        verify(
            webSocketBroadcastService,
            never()
        ).broadcastTrade(any());

    }

    @Test
    void shouldSaveTradeSuccessfully() {

        MarketEntity market =
            MarketEntity.builder()
                .marketId(1L)
                .yesPool(100L)
                .noPool(50L)
                .totalVolume(150L)
                .participants(1L)
                .build();

        when(
            tradeRepository.findByTxHash(
                "0xnew"
            )
        ).thenReturn(
            Optional.empty()
        );

        when(
            walletRepository
                .findByWalletAddressAndMarketIdAndYesPosition(
                    "0xwallet",
                    1L,
                    true
                )
        ).thenReturn(
            Optional.empty()
        );

        when(
            marketRepository.findById(
                1L
            )
        ).thenReturn(
            Optional.of(market)
        );

        when(
            tradeRepository.findByMarketId(
                1L
            )
        ).thenReturn(
            Collections.emptyList()
        );

        when(
            tradeRepository.findByTrader(
                "0xwallet"
            )
        ).thenReturn(
            Collections.emptyList()
        );

        when(
            leaderboardService.getLeaderboard()
        ).thenReturn(
            List.of()
        );

        when(
            analyticsService.getAnalytics()
        ).thenReturn(
            AnalyticsEntity.builder()
                .build()
        );

        marketSyncService.saveTrade(
            1L,
            "0xWallet",
            true,
            100L,
            "0xnew",
            1000L
        );

        verify(tradeRepository)
            .save(any(TradeEntity.class));

        verify(walletRepository)
            .save(any(WalletPositionEntity.class));

        verify(marketRepository)
            .save(any(MarketEntity.class));

        verify(webSocketBroadcastService)
            .broadcastPortfolio(any());

        verify(webSocketBroadcastService)
            .broadcastLeaderboard(any());

        verify(webSocketBroadcastService)
            .broadcastAnalytics(any());

        verify(webSocketBroadcastService)
            .broadcastMarket(any());

        verify(webSocketBroadcastService)
            .broadcastTrade(any());

    }

    @Test
    void shouldSaveEventAndBroadcast() {

        marketSyncService.saveEvent(
            "MARKET_CREATED",
            1L,
            "0xtxhash",
            12345L,
            "{\"question\":\"Will BTC reach 200k?\"}"
        );

        verify(eventRepository)
            .save(any(EventEntity.class));

        verify(webSocketBroadcastService)
            .broadcastEvent(any(EventEntity.class));

    }

    @Test
    void shouldReturnWhenMarketDoesNotExist() {

        when(
            walletRepository.findByMarketId(1L)
        ).thenReturn(
            Collections.emptyList()
        );

        when(
            marketRepository.findById(1L)
        ).thenReturn(
            Optional.empty()
        );

        marketSyncService.resolveMarket(
            1L,
            true
        );

        verify(
            walletRepository,
            never()
        ).save(any());

    }

    @Test
void shouldResolveWinningWallet() {

    MarketEntity market =
        MarketEntity.builder()
            .marketId(1L)
            .yesPool(1000L)
            .noPool(500L)
            .protocolFees(0L)
            .build();

    WalletPositionEntity winner =
        WalletPositionEntity.builder()
            .walletAddress("0xwallet")
            .marketId(1L)
            .yesPosition(true)
            .shares(100L)
            .investedAmount(100L)
            .build();

    when(
        walletRepository.findByMarketId(1L)
    ).thenReturn(
        List.of(winner)
    );

    when(
        marketRepository.findById(1L)
    ).thenReturn(
        Optional.of(market)
    );

    marketSyncService.resolveMarket(
        1L,
        true
    );

    verify(walletRepository)
        .save(any(WalletPositionEntity.class));

    verify(marketRepository)
        .save(any(MarketEntity.class));

}

@Test
void shouldResolveLosingWallet() {

    MarketEntity market =
        MarketEntity.builder()
            .marketId(1L)
            .yesPool(1000L)
            .noPool(500L)
            .protocolFees(0L)
            .build();

    WalletPositionEntity loser =
        WalletPositionEntity.builder()
            .walletAddress("0xloser")
            .marketId(1L)
            .yesPosition(false)
            .shares(50L)
            .investedAmount(50L)
            .currentValue(50L)
            .build();

    when(
        walletRepository.findByMarketId(1L)
    ).thenReturn(
        List.of(loser)
    );

    when(
        marketRepository.findById(1L)
    ).thenReturn(
        Optional.of(market)
    );

    marketSyncService.resolveMarket(
        1L,
        true
    );

    verify(walletRepository)
        .save(any(WalletPositionEntity.class));

    verify(marketRepository)
        .save(any(MarketEntity.class));

}

@Test
void shouldReturnWhenWalletPositionDoesNotExist() {

    when(
        walletRepository.findByWalletAddressAndMarketId(
            "0xwallet",
            1L
        )
    ).thenReturn(
        Optional.empty()
    );

    marketSyncService.markRewardClaimed(
        1L,
        "0xWallet",
        100L
    );

    verify(
        walletRepository,
        never()
    ).save(any());

    verify(
        webSocketBroadcastService,
        never()
    ).broadcastPortfolio(any());

}

@Test
void shouldReturnWhenWalletNotSettled() {

    WalletPositionEntity position =
        WalletPositionEntity.builder()
            .walletAddress("0xwallet")
            .marketId(1L)
            .settled(false)
            .claimed(false)
            .build();

    when(
        walletRepository.findByWalletAddressAndMarketId(
            "0xwallet",
            1L
        )
    ).thenReturn(
        Optional.of(position)
    );

    marketSyncService.markRewardClaimed(
        1L,
        "0xWallet",
        100L
    );

    verify(
        walletRepository,
        never()
    ).save(any());

    verify(
        webSocketBroadcastService,
        never()
    ).broadcastPortfolio(any());

}

}