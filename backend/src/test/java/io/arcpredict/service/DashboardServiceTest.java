package io.arcpredict.service;

import io.arcpredict.dto.DashboardResponse;
import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TreasuryReaderService treasuryReaderService;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void shouldReturnDashboardStatistics() throws Exception {

        when(
            marketRepository.findLatestBlock()
        ).thenReturn(
            12345L
        );

        when(
            marketRepository.count()
        ).thenReturn(
            5L
        );

        when(
            tradeRepository.count()
        ).thenReturn(
            20L
        );

        when(
            marketRepository.findTotalVolume()
        ).thenReturn(
            5000L
        );

        when(
            marketRepository.findTotalProtocolFees()
        ).thenReturn(
            250L
        );

        when(
            treasuryReaderService.getVaultBalance()
        ).thenReturn(
            10000L
        );

        when(
            treasuryReaderService.getTotalLiquidity()
        ).thenReturn(
            10000L
        );

        when(
            treasuryReaderService.getTotalLockedLiquidity()
        ).thenReturn(
            4000L
        );

        when(
            treasuryReaderService.getTotalReleasedLiquidity()
        ).thenReturn(
            6000L
        );

        when(
            marketRepository.countByResolved(false)
        ).thenReturn(
            3L
        );

        when(
            marketRepository.countByResolved(true)
        ).thenReturn(
            2L
        );

        DashboardResponse response =
            dashboardService.getDashboard();

        assertEquals(
            12345L,
            response.getLatestBlock()
        );

        assertEquals(
            5L,
            response.getTotalMarkets()
        );

        assertEquals(
            20L,
            response.getTotalTrades()
        );

        assertEquals(
            5000L,
            response.getTotalVolume()
        );

        assertEquals(
            250L,
            response.getTotalProtocolFees()
        );

        assertEquals(
            10000L,
            response.getVaultBalance()
        );

        assertEquals(
            10000L,
            response.getTotalLiquidity()
        );

        assertEquals(
            4000L,
            response.getTotalLockedLiquidity()
        );

        assertEquals(
            6000L,
            response.getAvailableLiquidity()
        );

        assertEquals(
            40.0,
            response.getTreasuryUtilization()
        );

        assertEquals(
            "Healthy",
            response.getTreasuryHealth()
        );

        assertEquals(
            3L,
            response.getActiveMarkets()
        );

        assertEquals(
            2L,
            response.getResolvedMarkets()
        );

    }

    @Test
void shouldReturnWarningTreasuryHealth() throws Exception {

    when(marketRepository.findLatestBlock()).thenReturn(1L);
    when(marketRepository.count()).thenReturn(1L);
    when(tradeRepository.count()).thenReturn(1L);

    when(marketRepository.findTotalVolume()).thenReturn(100L);
    when(marketRepository.findTotalProtocolFees()).thenReturn(10L);

    when(treasuryReaderService.getVaultBalance()).thenReturn(1000L);

    when(treasuryReaderService.getTotalLiquidity())
        .thenReturn(1000L);

    when(treasuryReaderService.getTotalLockedLiquidity())
        .thenReturn(700L);

    when(treasuryReaderService.getTotalReleasedLiquidity())
        .thenReturn(300L);

    when(marketRepository.countByResolved(false))
        .thenReturn(1L);

    when(marketRepository.countByResolved(true))
        .thenReturn(0L);

    DashboardResponse response =
        dashboardService.getDashboard();

    assertEquals(
        70.0,
        response.getTreasuryUtilization()
    );

    assertEquals(
        "Warning",
        response.getTreasuryHealth()
    );

}

}