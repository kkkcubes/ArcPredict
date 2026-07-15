package io.arcpredict.service;

import io.arcpredict.dto.InfrastructureMetricsResponse;
import io.arcpredict.dto.RpcHealthResponse;
import io.arcpredict.dto.SystemHealthResponse;

import io.arcpredict.repository.EventRepository;
import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemServiceTest {

    @Mock
    private RpcHealthService rpcHealthService;

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private SystemService systemService;

    @Test
    void shouldReturnInfrastructureMetrics() {

        RpcHealthResponse rpc =

            RpcHealthResponse.builder()
                .connected(true)
                .latestBlock(12345L)
                .latency(150L)
                .build();

        when(
            rpcHealthService.getRpcHealth()
        ).thenReturn(
            rpc
        );

        when(
            marketRepository.count()
        ).thenReturn(
            10L
        );

        when(
            tradeRepository.count()
        ).thenReturn(
            25L
        );

        when(
            eventRepository.count()
        ).thenReturn(
            40L
        );

        InfrastructureMetricsResponse metrics =

            systemService.getMetrics();

        assertEquals(
            true,
            metrics.getRpcConnected()
        );

        assertEquals(
            12345L,
            metrics.getLatestBlock()
        );

        assertEquals(
            150L,
            metrics.getRpcLatency()
        );

        assertEquals(
            10L,
            metrics.getTotalMarkets()
        );

        assertEquals(
            25L,
            metrics.getTotalTrades()
        );

        assertEquals(
            40L,
            metrics.getTotalEvents()
        );

        assertEquals(
            "Connected",
            metrics.getDatabaseStatus()
        );

        assertTrue(
            metrics.getUptimeSeconds() >= 0
        );

        assertTrue(
            metrics.getUsedMemoryMb() >= 0
        );

    }

    @Test
    void shouldReturnHealthyStatus() {

        RpcHealthResponse rpc =

            RpcHealthResponse.builder()
                .connected(true)
                .latestBlock(12345L)
                .latency(100L)
                .build();

        when(
            rpcHealthService.getRpcHealth()
        ).thenReturn(
            rpc
        );

        SystemHealthResponse health =

            systemService.getHealth();

        assertEquals(
            "UP",
            health.getStatus()
        );

        assertEquals(
            true,
            health.getDatabaseConnected()
        );

        assertEquals(
            true,
            health.getRpcConnected()
        );

        assertEquals(
            12345L,
            health.getLatestBlock()
        );

        assertNotNull(
            health.getTimestamp()
        );

    }

}