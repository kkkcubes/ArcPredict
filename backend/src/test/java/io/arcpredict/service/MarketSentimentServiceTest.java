package io.arcpredict.service;

import io.arcpredict.dto.MarketSentimentResponse;
import io.arcpredict.entity.MarketEntity;

import io.arcpredict.repository.MarketRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketSentimentServiceTest {

    @Mock
    private MarketRepository marketRepository;

    @InjectMocks
    private MarketSentimentService marketSentimentService;

    @Test
    void shouldCalculateMarketSentiment() {

        MarketEntity market =
            MarketEntity.builder()
                .marketId(1L)
                .yesPool(300L)
                .noPool(100L)
                .build();

        when(
            marketRepository.findAll()
        ).thenReturn(
            List.of(market)
        );

        List<MarketSentimentResponse> response =
            marketSentimentService
                .getMarketSentiment();

        assertEquals(
            1,
            response.size()
        );

        MarketSentimentResponse sentiment =
            response.get(0);

        assertEquals(
            1L,
            sentiment.getMarketId()
        );

        assertEquals(
            300L,
            sentiment.getYesPool()
        );

        assertEquals(
            100L,
            sentiment.getNoPool()
        );

        assertEquals(
            400L,
            sentiment.getTotalPool()
        );

        assertEquals(
            75.0,
            sentiment.getYesPercentage(),
            0.01
        );

        assertEquals(
            25.0,
            sentiment.getNoPercentage(),
            0.01
        );

    }

    @Test
void shouldHandleEmptyPools() {

    MarketEntity market =
        MarketEntity.builder()
            .marketId(2L)
            .yesPool(null)
            .noPool(null)
            .build();

    when(
        marketRepository.findAll()
    ).thenReturn(
        List.of(market)
    );

    List<MarketSentimentResponse> response =
        marketSentimentService
            .getMarketSentiment();

    assertEquals(
        1,
        response.size()
    );

    MarketSentimentResponse sentiment =
        response.get(0);

    assertEquals(
        0L,
        sentiment.getYesPool()
    );

    assertEquals(
        0L,
        sentiment.getNoPool()
    );

    assertEquals(
        0L,
        sentiment.getTotalPool()
    );

    assertEquals(
        0.0,
        sentiment.getYesPercentage(),
        0.001
    );

    assertEquals(
        0.0,
        sentiment.getNoPercentage(),
        0.001
    );

}

}