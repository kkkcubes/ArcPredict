package io.arcpredict.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final MeterRegistry meterRegistry;

    // Gauges
    private final AtomicLong latestBlockchainBlock = new AtomicLong(0);
    private final AtomicLong lastProcessedBlock = new AtomicLong(0);
    private final AtomicLong rpcLatency = new AtomicLong(0);
    private final AtomicLong websocketConnections = new AtomicLong(0);

    // Counters
    private Counter marketsProcessedCounter;
    private Counter tradesProcessedCounter;
    private Counter receiptFailureCounter;

    @PostConstruct
    public void init() {

        System.out.println("====================================");
        System.out.println("MetricsService initialized");
        System.out.println("====================================");

        registerMetrics();
    }

    private void registerMetrics() {

        Gauge.builder(
                "arcpredict.blockchain.latest_block",
                latestBlockchainBlock,
                value -> value.get()
        ).register(meterRegistry);

        Gauge.builder(
                "arcpredict.blockchain.last_processed_block",
                lastProcessedBlock,
                value -> value.get()
        ).register(meterRegistry);

        Gauge.builder(
                "arcpredict.rpc.latency",
                rpcLatency,
                value -> value.get()
        ).register(meterRegistry);

        Gauge.builder(
                "arcpredict.websocket.connections",
                websocketConnections,
                value -> value.get()
        ).register(meterRegistry);

        marketsProcessedCounter =
                meterRegistry.counter("arcpredict.market.processed");

        tradesProcessedCounter =
                meterRegistry.counter("arcpredict.trade.processed");

        receiptFailureCounter =
                meterRegistry.counter("arcpredict.receipt.failures");
    }

    // ---------- Gauge setters ----------

    public void setLatestBlockchainBlock(long block) {
        latestBlockchainBlock.set(block);
    }

    public void setLastProcessedBlock(long block) {
        lastProcessedBlock.set(block);
    }

    public void setRpcLatency(long latencyMs) {
        rpcLatency.set(latencyMs);
    }

    public void setWebSocketConnections(long connections) {
        websocketConnections.set(connections);
    }

    // ---------- Counter methods ----------

    public void incrementMarketsProcessed() {
        marketsProcessedCounter.increment();
    }

    public void incrementTradesProcessed() {
        tradesProcessedCounter.increment();
    }

    public void incrementReceiptFailures() {
        receiptFailureCounter.increment();
    }
}