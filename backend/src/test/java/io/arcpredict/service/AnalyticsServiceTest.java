package io.arcpredict.service;

import io.arcpredict.entity.AnalyticsEntity;
import io.arcpredict.entity.MarketEntity;

import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}