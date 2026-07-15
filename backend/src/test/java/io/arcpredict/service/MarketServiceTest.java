package io.arcpredict.service;

import io.arcpredict.entity.MarketEntity;
import io.arcpredict.repository.MarketRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MarketServiceTest {

    @Mock
    private MarketRepository marketRepository;

    @InjectMocks
    private MarketService marketService;

    @Test
    void shouldReturnAllMarkets() {

        List<MarketEntity> markets =
            List.of(
                MarketEntity.builder()
                    .marketId(1L)
                    .question("Market 1")
                    .build(),
                MarketEntity.builder()
                    .marketId(2L)
                    .question("Market 2")
                    .build()
            );

        when(
            marketRepository.findAll()
        ).thenReturn(
            markets
        );

        List<MarketEntity> result =
            marketService.getMarkets();

        assertEquals(
            2,
            result.size()
        );

        verify(
            marketRepository
        ).findAll();

    }

    @Test
    void shouldReturnMarketsPage() {

        PageRequest pageable =
            PageRequest.of(
                0,
                10
            );

        Page<MarketEntity> page =
            new PageImpl<>(
                List.of(
                    MarketEntity.builder()
                        .marketId(1L)
                        .build()
                )
            );

        when(
            marketRepository.findAll(
                pageable
            )
        ).thenReturn(
            page
        );

        Page<MarketEntity> result =
            marketService.getMarketsPage(
                pageable
            );

        assertEquals(
            1,
            result.getTotalElements()
        );

    }

    @Test
    void shouldReturnMarketById() {

        MarketEntity market =
            MarketEntity.builder()
                .marketId(10L)
                .question("BTC")
                .build();

        when(
            marketRepository.findById(
                10L
            )
        ).thenReturn(
            Optional.of(
                market
            )
        );

        MarketEntity result =
            marketService.getMarket(
                10L
            );

        assertEquals(
            10L,
            result.getMarketId()
        );

    }

    @Test
    void shouldThrowWhenMarketNotFound() {

        when(
            marketRepository.findById(
                99L
            )
        ).thenReturn(
            Optional.empty()
        );

        assertThrows(
            java.util.NoSuchElementException.class,
            () -> marketService.getMarket(
                99L
            )
        );

    }

    @Test
    void shouldSaveMarket() {

        MarketEntity market =
            MarketEntity.builder()
                .marketId(5L)
                .question("ETH")
                .build();

        when(
            marketRepository.save(
                market
            )
        ).thenReturn(
            market
        );

        MarketEntity saved =
            marketService.save(
                market
            );

        assertEquals(
            5L,
            saved.getMarketId()
        );

        verify(
            marketRepository
        ).save(
            market
        );

    }

    @Test
    void shouldReturnActiveMarkets() {

        List<MarketEntity> active =
            List.of(
                MarketEntity.builder()
                    .marketId(1L)
                    .resolved(false)
                    .build()
            );

        when(
            marketRepository.findByResolved(
                false
            )
        ).thenReturn(
            active
        );

        List<MarketEntity> result =
            marketService.activeMarkets();

        assertEquals(
            1,
            result.size()
        );

        verify(
            marketRepository
        ).findByResolved(
            false
        );

    }

}