package io.arcpredict.service;

import io.arcpredict.dto.AnalyticsHistoryResponse;

import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.entity.MarketEntity;
import io.arcpredict.entity.TradeEntity;

import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void shouldCalculateAnalytics() {

        MarketEntity market1 =
            MarketEntity.builder()
                .marketId(1L)
                .yesPool(100L)
                .noPool(50L)
                .totalVolume(150L)
                .build();

        MarketEntity market2 =
            MarketEntity.builder()
                .marketId(2L)
                .yesPool(300L)
                .noPool(50L)
                .totalVolume(350L)
                .build();

        when(
            marketRepository.findAll()
        ).thenReturn(
            List.of(
                market1,
                market2
            )
        );

        when(
            tradeRepository.count()
        ).thenReturn(
            10L
        );

        when(
            marketRepository.countByResolved(true)
        ).thenReturn(
            1L
        );

        AnalyticsEntity analytics =
            analyticsService.getAnalytics();

        assertEquals(
            2L,
            analytics.getTotalMarkets()
        );

        assertEquals(
            500L,
            analytics.getTotalVolume()
        );

        assertEquals(
            10L,
            analytics.getTotalTraders()
        );

        assertEquals(
            1L,
            analytics.getResolvedMarkets()
        );

        assertEquals(
            500L,
            analytics.getOpenInterest()
        );

        assertEquals(
            80.0,
            analytics.getBullishPercentage(),
            0.01
        );

        assertEquals(
            20.0,
            analytics.getBearishPercentage(),
            0.01
        );

    }

    @Test
    void shouldBuildDailyVolumeHistory() {

        TradeEntity trade1 =
            TradeEntity.builder()
                .amount(100L)
                .timestamp(
                    Instant.parse(
                        "2026-07-15T10:00:00Z"
                    )
                )
                .build();

        TradeEntity trade2 =
            TradeEntity.builder()
                .amount(200L)
                .timestamp(
                    Instant.parse(
                        "2026-07-15T15:00:00Z"
                    )
                )
                .build();

        when(
            tradeRepository.findAll()
        ).thenReturn(
            List.of(
                trade1,
                trade2
            )
        );

        when(
            marketRepository.findAll()
        ).thenReturn(
            List.of()
        );

        AnalyticsHistoryResponse response =
            analyticsService.getAnalyticsHistory();

        assertEquals(
            1,
            response.getDailyVolume().size()
        );

        assertEquals(
            300L,
            response
                .getDailyVolume()
                .get(0)
                .getValue()
        );

    }

}