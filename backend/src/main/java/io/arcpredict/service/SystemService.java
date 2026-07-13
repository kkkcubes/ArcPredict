package io.arcpredict.service;

import io.arcpredict.dto.InfrastructureMetricsResponse;
import io.arcpredict.dto.RpcHealthResponse;
import io.arcpredict.dto.SystemHealthResponse;

import io.arcpredict.repository.EventRepository;
import io.arcpredict.repository.MarketRepository;
import io.arcpredict.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Service
@RequiredArgsConstructor
public class SystemService {

    private final RpcHealthService rpcHealthService;

    private final MarketRepository marketRepository;

    private final TradeRepository tradeRepository;

    private final EventRepository eventRepository;

    public InfrastructureMetricsResponse getMetrics() {

        RpcHealthResponse rpc =
            rpcHealthService.getRpcHealth();

        long uptimeSeconds =
            ManagementFactory
                .getRuntimeMXBean()
                .getUptime() / 1000;

        Runtime runtime =
            Runtime.getRuntime();

        long usedMemoryMb =

            (

                runtime.totalMemory()

                -

                runtime.freeMemory()

            )

            / (1024 * 1024);

        return InfrastructureMetricsResponse

            .builder()

            .rpcConnected(
                rpc.getConnected()
            )

            .latestBlock(
                rpc.getLatestBlock()
            )

            .rpcLatency(
                rpc.getLatency()
            )

            .totalMarkets(
                marketRepository.count()
            )

            .totalTrades(
                tradeRepository.count()
            )

            .totalEvents(
                eventRepository.count()
            )

            .websocketClients(
                0L
            )

            .uptimeSeconds(
                uptimeSeconds
            )

            .usedMemoryMb(
                usedMemoryMb
            )

            .databaseStatus(
                "Connected"
            )

            .build();

    }

    public SystemHealthResponse getHealth() {

        RpcHealthResponse rpc =
            rpcHealthService.getRpcHealth();

        return SystemHealthResponse

            .builder()

            .status(

                Boolean.TRUE.equals(
                    rpc.getConnected()
                )

                    ? "UP"

                    : "DOWN"

            )

            .databaseConnected(
                true
            )

            .rpcConnected(
                rpc.getConnected()
            )

            .latestBlock(
                rpc.getLatestBlock()
            )

            .timestamp(
                System.currentTimeMillis()
            )

            .build();

    }

}