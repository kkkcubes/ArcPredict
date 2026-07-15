package io.arcpredict.service;

import io.arcpredict.entity.WalletPositionEntity;

import io.arcpredict.repository.TradeRepository;
import io.arcpredict.repository.WalletRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @Test
    void shouldReturnPortfolio() {

        WalletPositionEntity position1 =
            WalletPositionEntity.builder()
                .walletAddress("0xwallet")
                .marketId(1L)
                .shares(100L)
                .build();

        WalletPositionEntity position2 =
            WalletPositionEntity.builder()
                .walletAddress("0xwallet")
                .marketId(2L)
                .shares(50L)
                .build();

        when(
            walletRepository.findByWalletAddress(
                "0xwallet"
            )
        ).thenReturn(
            List.of(
                position1,
                position2
            )
        );

        List<WalletPositionEntity> portfolio =
            portfolioService.getPortfolio(
                "0xwallet"
            );

        assertEquals(
            2,
            portfolio.size()
        );

        assertEquals(
            1L,
            portfolio.get(0).getMarketId()
        );

        assertEquals(
            2L,
            portfolio.get(1).getMarketId()
        );

    }

    @Test
void shouldSavePosition() {

    WalletPositionEntity position =
        WalletPositionEntity.builder()
            .walletAddress("0xwallet")
            .marketId(1L)
            .shares(100L)
            .investedAmount(500L)
            .build();

    when(
        walletRepository.save(position)
    ).thenReturn(
        position
    );

    WalletPositionEntity saved =
        portfolioService.savePosition(
            position
        );

    assertEquals(
        position,
        saved
    );

}

}